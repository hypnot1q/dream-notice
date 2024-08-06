package cc.dreamcode.notice.bukkit;

import cc.dreamcode.notice.Notice;
import cc.dreamcode.notice.NoticeSender;
import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.minecraft.AdventureLegacy;
import cc.dreamcode.notice.minecraft.AdventureNotice;
import java.time.Duration;
import java.util.Map;
import lombok.NonNull;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BukkitNotice extends AdventureNotice<BukkitNotice>
    implements NoticeSender<CommandSender> {

  public BukkitNotice(@NonNull NoticeType noticeType, @NonNull String... noticeText) {
    super(noticeType, noticeText);
  }

  public static BukkitNotice of(@NonNull NoticeType noticeType, @NonNull String... noticeText) {
    return new BukkitNotice(noticeType, noticeText);
  }

  public static BukkitNotice chat(@NonNull String... noticeText) {
    return new BukkitNotice(NoticeType.CHAT, noticeText);
  }

  public static BukkitNotice actionBar(@NonNull String... noticeText) {
    return new BukkitNotice(NoticeType.ACTION_BAR, noticeText);
  }

  public static BukkitNotice title(@NonNull String... noticeText) {
    return new BukkitNotice(NoticeType.TITLE, noticeText);
  }

  public static BukkitNotice subtitle(@NonNull String... noticeText) {
    return new BukkitNotice(NoticeType.SUBTITLE, noticeText);
  }

  public static BukkitNotice titleSubtitle(@NonNull String... noticeText) {
    return new BukkitNotice(NoticeType.TITLE_SUBTITLE, noticeText);
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
    final BukkitAudiences bukkitAudiences = BukkitNoticeProvider.getInstance().getBukkitAudiences();
    this.sendFormatted(target, bukkitAudiences.sender(target));
  }

  private void sendFormatted(@NonNull CommandSender sender, @NonNull Audience target) {

    if (!(sender instanceof Player)) {
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
