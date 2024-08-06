package cc.dreamcode.notice.bungee;

import cc.dreamcode.notice.Notice;
import cc.dreamcode.notice.NoticeSender;
import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.minecraft.AdventureLegacy;
import cc.dreamcode.notice.minecraft.AdventureNotice;
import java.time.Duration;
import java.util.Map;
import lombok.NonNull;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeNotice extends AdventureNotice<BungeeNotice>
    implements NoticeSender<CommandSender> {
  public BungeeNotice(@NonNull NoticeType noticeType, @NonNull String... noticeText) {
    super(noticeType, noticeText);
  }

  public static BungeeNotice of(@NonNull NoticeType noticeType, @NonNull String... noticeText) {
    return new BungeeNotice(noticeType, noticeText);
  }

  public static BungeeNotice chat(@NonNull String... noticeText) {
    return new BungeeNotice(NoticeType.CHAT, noticeText);
  }

  public static BungeeNotice actionBar(@NonNull String... noticeText) {
    return new BungeeNotice(NoticeType.ACTION_BAR, noticeText);
  }

  public static BungeeNotice title(@NonNull String... noticeText) {
    return new BungeeNotice(NoticeType.TITLE, noticeText);
  }

  public static BungeeNotice subtitle(@NonNull String... noticeText) {
    return new BungeeNotice(NoticeType.TITLE, noticeText);
  }

  public static BungeeNotice titleSubtitle(@NonNull String... noticeText) {
    return new BungeeNotice(NoticeType.TITLE_SUBTITLE, noticeText);
  }

  @Override
  public void send(@NonNull CommandSender target) {
    this.wrapAndSend(target);
    this.clearRender();
  }

  @Override
  public void send(@NonNull CommandSender target, @NonNull Map<String, Object> mapReplacer) {
    this.with(mapReplacer).wrapAndSend(target);
    this.clearRender();
  }

  private void wrapAndSend(@NonNull CommandSender target) {
    final BungeeAudiences bungeeAudiences = BungeeNoticeProvider.getInstance().getBungeeAudiences();
    this.sendFormatted(target, bungeeAudiences.sender(target));
  }

  private void sendFormatted(@NonNull CommandSender sender, @NonNull Audience target) {

    if (!(sender instanceof ProxiedPlayer)) {
      this.toSplitComponents().forEach(target::sendMessage);
      return;
    }

    final NoticeType noticeType = (NoticeType) this.getNoticeType();
    switch (noticeType) {
      case DO_NOT_SEND -> this.clearRender();
      case CHAT -> this.toSplitComponents().forEach(target::sendMessage);
      case ACTION_BAR -> target.sendActionBar(this.toJoiningComponent());
      case TITLE -> {
        final Component component = this.toJoiningComponent();
        final Component emptyComponent = AdventureLegacy.deserialize(" ");

        Title titleBuilder =
            Title.title(
                component,
                emptyComponent,
                Title.Times.times(
                    Duration.ofMillis(this.getTitleFadeIn() * 50L),
                    Duration.ofMillis(this.getTitleStay() * 50L),
                    Duration.ofMillis(this.getTitleFadeOut() * 50L)));

        target.showTitle(titleBuilder);
      }
      case SUBTITLE -> {
        final Component component = AdventureLegacy.deserialize(" ");
        final Component emptyComponent = this.toJoiningComponent();

        Title titleBuilder =
            Title.title(
                component,
                emptyComponent,
                Title.Times.times(
                    Duration.ofMillis(this.getTitleFadeIn() * 50L),
                    Duration.ofMillis(this.getTitleStay() * 50L),
                    Duration.ofMillis(this.getTitleFadeOut() * 50L)));

        target.showTitle(titleBuilder);
      }
      case TITLE_SUBTITLE -> {
        String[] split = this.getRender().split(Notice.lineSeparator());
        if (split.length == 0) {
          throw new RuntimeException(
              "Notice with TITLE_SUBTITLE need line-separator ("
                  + Notice.lineSeparator()
                  + ") to separate two messages.");
        }

        final Component title = AdventureLegacy.deserialize(split[0]);
        final Component subTitle = AdventureLegacy.deserialize(split[1]);

        Title titleBuilder =
            Title.title(
                title,
                subTitle,
                Title.Times.times(
                    Duration.ofMillis(this.getTitleFadeIn() * 50L),
                    Duration.ofMillis(this.getTitleStay() * 50L),
                    Duration.ofMillis(this.getTitleFadeOut() * 50L)));

        target.showTitle(titleBuilder);
      }
      default -> {
        this.clearRender();
        throw new RuntimeException("Cannot resolve notice-type. (" + this.getNoticeType() + ")");
      }
    }
  }
}
