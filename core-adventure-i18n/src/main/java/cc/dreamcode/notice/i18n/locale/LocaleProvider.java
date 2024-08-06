package cc.dreamcode.notice.i18n.locale;

import java.util.Locale;

public interface LocaleProvider<V> {

  Locale getLocale(V viewer);
}
