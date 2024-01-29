package com.example.example_mod;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

public class ExampleMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Example Mod");
	public static final String MOD_ID = "quilt_config_example_mod";

	@Override
	public void onInitialize(ModContainer mod) {
		ExampleModConfig config = ExampleModConfig.INSTANCE;

		if (config.print.value()) {
			for (int i = 0; i < config.timesToPrint.value(); i++) {
				PrintStream stream = config.advancedSettings.printStream.value().getStream();
				String message = config.message.value();
				for (var entry : config.replacements.value()) {
					message = message.replace(entry.getKey(), entry.getValue());
				}

				if (config.advancedSettings.printNewlines.value()) {
					stream.println(config.message.value());
				} else {
					stream.print(config.message.value());
				}
			}
		}
	}
}
