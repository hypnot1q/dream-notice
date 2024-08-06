package cc.dreamcode.notice.velocity;

import cc.dreamcode.notice.Notice;
import cc.dreamcode.notice.NoticeSender;
import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.minecraft.AdventureLegacy;
import cc.dreamcode.notice.minecraft.AdventureNotice;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import java.time.Duration;
import java.util.Map;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;

public class VelocityNotice extends AdventureNotice<VelocityNotice>
    implements NoticeSender<CommandSource> {

  public VelocityNotice(@NonNull NoticeType noticeType, @NonNull String... noticeText) {
    super(noticeType, noticeText);
  }

  public static VelocityNotice of(@NonNull NoticeType noticeType, @NonNull String... noticeText) {
    return new VelocityNotice(noticeType, noticeText);
  }

  public static VelocityNotice chat(@NonNull String... noticeText) {
    return new VelocityNotice(NoticeType.CHAT, noticeText);
  }

  public static VelocityNotice actionBar(@NonNull String... noticeText) {
    return new VelocityNotice(NoticeType.ACTION_BAR, noticeText);
  }

  public static VelocityNotice title(@NonNull String... noticeText) {
    return new VelocityNotice(NoticeType.TITLE, noticeText);
  }

  public static VelocityNotice subtitle(@NonNull String... noticeText) {
    return new VelocityNotice(NoticeType.TITLE, noticeText);
  }

  public static VelocityNotice titleSubtitle(@NonNull String... noticeText) {
    return new VelocityNotice(NoticeType.TITLE_SUBTITLE, noticeText);
  }

  @Override
  public void send(@NonNull CommandSource target) {
    this.sendFormatted(target);
  }

  @Override
  public void send(@NonNull CommandSource target, @NonNull Map<String, Object> mapReplacer) {
    this.with(mapReplacer).send(target);
  }

  private void sendFormatted(@NonNull CommandSource target) {

    if (!(target instanceof Player)) {
      this.toSplitComponents().forEach(target::sendMessage);

      this.clearRender();
      return;
    }

    final NoticeType noticeType = (NoticeType) this.getNoticeType();
    switch (noticeType) {
      case DO_NOT_SEND -> this.clearRender();
      case CHAT -> {
        this.toSplitComponents().forEach(target::sendMessage);

        this.clearRender();
      }
      case ACTION_BAR -> {
        target.sendActionBar(this.toJoiningComponent());

        this.clearRender();
      }
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

        this.clearRender();
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

        this.clearRender();
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

        this.clearRender();
      }
      default -> {
        this.clearRender();
        throw new RuntimeException("Cannot resolve notice-type. (" + this.getNoticeType() + ")");
      }
    }
  }
}
