package com.example.example_mod;

import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.annotations.IntegerRange;
import org.quiltmc.config.api.annotations.Processor;
import org.quiltmc.config.api.values.ConfigSerializableObject;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.config.api.values.ValueList;
import org.quiltmc.config.api.values.ValueMap;
import org.quiltmc.loader.api.config.v2.QuiltConfig;

import java.io.PrintStream;

public class ExampleModConfig extends ReflectiveConfig {
	public static final ExampleModConfig INSTANCE = QuiltConfig.create(ExampleMod.MOD_ID, ExampleMod.MOD_ID, ExampleModConfig.class);

	public final TrackedValue<String> message = this.value("rai minecraft :thumbsup:");
	@IntegerRange(min = 0, max = 10)
	public final TrackedValue<Integer> timesToPrint = this.value(1);
	@Comment("Whether to print the message at all.")
	public final TrackedValue<Boolean> print = this.value(true);
	@Comment("When a key in this map shows up in the message, it'll be replaced with the corresponding value.")
	public final TrackedValue<ValueMap<String>> replacements = this.map("")
		.put("oro armor", "rai minecraft")
		.put("lambda aurora", "rai minecraft")
		.put("tib s", "rai minecraft")
		.build();
	@Comment("This isn't actually used by the mod, but I was completely out of ideas for things to add.")
	public final TrackedValue<ValueList<String>> typesOfSoup = this.list("", "tomato", "borscht", "chicken noodle", "ramen", "STEW", "mushroom");

	@Processor("checkAdvancedSettings")
	public final AdvancedSettings advancedSettings = new AdvancedSettings();

	// IDEs likes IntelliJ idea won't know that this method is being called by Quilt Config code
	@SuppressWarnings("unused")
	public void checkAdvancedSettings(AdvancedSettings settings) {
		if (!settings.printNewlines.value().equals(settings.printNewlines.getDefaultValue())
				|| !settings.printStream.value().equals(settings.printStream.value())) {
			ExampleMod.LOGGER.info("Advanced settings are being used in the configuration!");
		}
	}

	public static class AdvancedSettings extends Section {
		public final TrackedValue<Boolean> printNewlines = this.value(true);
		public final TrackedValue<PrintStreamOption> printStream = this.value(PrintStreamOption.SYSTEM_OUT);

		@SuppressWarnings("unused")
		public enum PrintStreamOption implements ConfigSerializableObject<String> {
			SYSTEM_OUT(System.out),
			SYSTEM_ERROR(System.err);

			private final PrintStream printStream;

			PrintStreamOption(PrintStream stream) {
				this.printStream = stream;
			}

			public PrintStream getStream() {
				return this.printStream;
			}

			@Override
			public PrintStreamOption convertFrom(String representation) {
				return valueOf(representation);
			}

			@Override
			public String getRepresentation() {
				return this.name();
			}

			@Override
			public PrintStreamOption copy() {
				// enum values cannot be duplicated
				return this;
			}
		}
	}
}
