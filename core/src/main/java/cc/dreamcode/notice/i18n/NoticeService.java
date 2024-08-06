package cc.dreamcode.notice.i18n;

import static cc.dreamcode.notice.i18n.NoticeSourceUtils.getLocaleByFile;
import static cc.dreamcode.utilities.ResourceUtils.unpackResources;
import static java.nio.file.Files.exists;
import static java.util.Collections.unmodifiableSet;

import cc.dreamcode.notice.Notice;
import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.i18n.locale.LocaleProvider;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.configurer.Configurer;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.audience.Audience;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class NoticeService<V extends Audience, N extends Notice<N>> {

  protected final LocaleProvider<V> localeProvider;
  private final Class<N> type;
  private final Locale fallbackLocale;
  private final OkaeriSerdesPack serdesPack;
  private final Map<Locale, Map<String, N>> messagesByLocales = new ConcurrentHashMap<>();
  private final Supplier<Configurer> configurer;

  public abstract N getNoticeToPersist(NoticeType noticeType, String message);

  public void registerNoticeSource(final Locale locale, final NoticeSource noticeSource) {
    Map<String, Object> valuesByKeys = noticeSource.asMap(configurer.get(), true);
    Map<String, N> messagesByKeys = new LinkedHashMap<>();
    for (Entry<String, Object> entry : valuesByKeys.entrySet()) {
      N notice = noticeSource.get(entry.getKey(), type);
      notice.setLocale(locale);
      messagesByKeys.put(entry.getKey(), notice);
    }

    messagesByLocales.computeIfAbsent(locale, key -> new LinkedHashMap<>()).putAll(messagesByKeys);
  }

  public void registerResources(File jarFile, File dataPath, Set<String> paths) {
    unpackResources(jarFile, dataPath, paths, false, configurer.get().getExtensions())
        .forEach(this::registerResource);
  }

  public void registerResource(final File resourceFile) {
    registerNoticeSource(
        getLocaleByFile(resourceFile),
        ConfigManager.create(
            NoticeSource.class,
            it -> {
              it.withConfigurer(configurer.get())
                  .withBindFile(resourceFile)
                  .withSerdesPack(serdesPack);
              it.getDeclaration().getFields().forEach(field -> field.updateValue(null));
              if (exists(it.getBindFile())) {
                it.load();
              }
            }));
  }

  public void reloadResources(File jarFile, File dataPath, Set<String> paths) {
    messagesByLocales.clear();
    registerResources(jarFile, dataPath, paths);
  }

  public N getNotice(Locale locale, String key) {
    if (messagesByLocales.containsKey(locale)) {
      return messagesByLocales.get(locale).get(key);
    }

    Locale strippedLocale = Locale.of(locale.getLanguage());
    if (messagesByLocales.containsKey(strippedLocale)) {
      return messagesByLocales.get(strippedLocale).get(key);
    }

    if (messagesByLocales.containsKey(fallbackLocale)) {
      return messagesByLocales.get(fallbackLocale).get(key);
    }

    return getNoticeToPersist(NoticeType.CHAT, key);
  }

  public N getNotice(V viewer, String key) {
    return getNotice(localeProvider.getLocale(viewer), key);
  }

  public Set<Locale> getAvailableLocales() {
    return unmodifiableSet(messagesByLocales.keySet());
  }
}
