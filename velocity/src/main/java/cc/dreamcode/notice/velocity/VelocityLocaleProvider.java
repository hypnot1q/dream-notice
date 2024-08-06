package cc.dreamcode.notice.velocity;

import cc.dreamcode.notice.i18n.locale.LocaleProvider;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import java.util.Locale;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class VelocityLocaleProvider implements LocaleProvider<CommandSource> {

  private final Locale fallbackLocale;

  @Override
  public Locale getLocale(final CommandSource viewer) {
    return viewer instanceof Player player ? player.getEffectiveLocale() : fallbackLocale;
  }
}
