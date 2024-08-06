package cc.dreamcode.notice.i18n;

import java.io.File;
import java.util.Locale;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class NoticeSourceUtils {

  public static Locale getLocaleByFile(final File file) {
    final String fileName = file.getName();
    final int lastUnderscoreIndex = fileName.lastIndexOf('_');
    final int lastDotIndex = fileName.lastIndexOf('.');
    final String languageTag = fileName.substring(lastUnderscoreIndex + 1, lastDotIndex);
    return Locale.forLanguageTag(languageTag);
  }
}
