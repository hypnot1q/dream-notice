package cc.dreamcode.notice;

import cc.dreamcode.utilities.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeImpl<R extends Notice<R>> extends Notice<R> {

  private final NoticeType noticeType;
  private final String noticeText;

  private int titleFadeIn = 10;
  private int titleStay = 20;
  private int titleFadeOut = 10;

  public NoticeImpl(NoticeType noticeType, String... noticeText) {
    this.noticeType = noticeType;

    if (noticeText.length == 1) {
      this.noticeText = noticeText[0];
      return;
    }

    this.noticeText = StringUtil.join(noticeText, Notice.lineSeparator());
  }

  @Override
  public String getRaw() {
    return this.noticeText;
  }

  @Override
  public Enum<?> getNoticeType() {
    return this.noticeType;
  }
}
