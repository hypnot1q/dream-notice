package cc.dreamcode.notice.velocity;

import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.i18n.NoticeService;
import com.velocitypowered.api.command.CommandSource;
import eu.okaeri.configs.configurer.Configurer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class VelocityNoticeService extends NoticeService<CommandSource, VelocityNotice> {

  public VelocityNoticeService(Supplier<Configurer> configurer, Locale fallbackLocale) {
    super(
        VelocityNotice.class,
        configurer,
        fallbackLocale,
        new VelocityLocaleProvider(fallbackLocale),
        registry -> registry.register(new VelocityNoticeSerializer()));
  }

  public static VelocityNoticeService create(
      Supplier<Configurer> configurer, Locale fallbackLocale) {
    return new VelocityNoticeService(configurer, fallbackLocale);
  }

  @Override
  public VelocityNotice getNoticeToPersist(final NoticeType noticeType, final String message) {
    return VelocityNotice.of(noticeType, message);
  }

  public void sendAll(final Collection<CommandSource> receivers, final String message) {
    Map<Locale, VelocityNotice> noticesByLocales = new HashMap<>();
    receivers.forEach(
        receiver -> {
          VelocityNotice notice =
              noticesByLocales.computeIfAbsent(
                  localeProvider.getLocale(receiver), l -> getNotice(l, message));
          notice.send(receiver);
        });
  }
}
