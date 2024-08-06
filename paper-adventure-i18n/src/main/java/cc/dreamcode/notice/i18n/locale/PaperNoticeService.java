package cc.dreamcode.notice.i18n.locale;

import cc.dreamcode.notice.adventure.PaperNotice;
import cc.dreamcode.notice.adventure.serializer.PaperNoticeSerializer;
import cc.dreamcode.notice.i18n.NoticeService;
import cc.dreamcode.notice.minecraft.NoticeType;
import eu.okaeri.configs.configurer.Configurer;
import java.util.Locale;
import java.util.function.Supplier;
import org.bukkit.command.CommandSender;

public class PaperNoticeService extends NoticeService<CommandSender, PaperNotice> {

  public PaperNoticeService(Supplier<Configurer> configurer, Locale fallbackLocale) {
    super(
        PaperNotice.class,
        configurer,
        fallbackLocale,
        new PaperLocaleProvider(fallbackLocale),
        registry -> registry.register(new PaperNoticeSerializer()));
  }

  public static PaperNoticeService create(Supplier<Configurer> configurer, Locale fallbackLocale) {
    return new PaperNoticeService(configurer, fallbackLocale);
  }

  @Override
  public PaperNotice getNoticeToPersist(final NoticeType noticeType, final String message) {
    return PaperNotice.of(noticeType, message);
  }
}
