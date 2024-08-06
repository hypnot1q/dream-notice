package cc.dreamcode.notice.paper;

import cc.dreamcode.notice.i18n.locale.LocaleProvider;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final class PaperLocaleProvider implements LocaleProvider<CommandSender> {

  private final Locale fallbackLocale;

  @Override
  public Locale getLocale(final CommandSender viewer) {
    return viewer instanceof Player player ? player.locale() : fallbackLocale;
  }
}
