package cc.dreamcode.notice.minecraft;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import net.kyori.adventure.text.Component;

public class AdventureLegacyColor implements UnaryOperator<Component> {

  @Override
  public Component apply(Component component) {
    return component.replaceText(
        builder ->
            builder
                .match(Pattern.compile(".*"))
                .replacement(
                    (matchResult, builder1) -> AdventureLegacy.component(matchResult.group())));
  }
}
