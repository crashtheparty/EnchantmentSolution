package org.ctp.enchantmentsolution.utils.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleConfigManager {
	private JavaPlugin plugin;

	public SimpleConfigManager(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public SimpleConfig getNewConfig(String filePath, String[] header) {
		File file = getConfigFile(filePath);

		if (!file.exists()) {
			prepareFile(filePath);

			if ((header != null) && (header.length != 0)) {
				setHeader(file, header);
			}
		}

		SimpleConfig config = new SimpleConfig(
				file, getCommentsNum(file), plugin);
		return config;
	}

	public SimpleConfig getNewConfig(String filePath) {
		return getNewConfig(filePath, null);
	}

	private File getConfigFile(String file) {
		if ((file.isEmpty()) || (file == null)) {
			return null;
		}

		File configFile;
		if (file.contains("/")) {
			if (file.startsWith("/")) {
				configFile = new File(plugin.getDataFolder()
						+ file.replace("/", File.separator));
			} else {
				configFile = new File(plugin.getDataFolder() + File.separator
						+ file.replace("/", File.separator));
			}
		} else {
			configFile = new File(plugin.getDataFolder(), file);
		}

		return configFile;
	}

	public void prepareFile(String filePath, String resource) {
		File file = getConfigFile(filePath);

		if (file.exists()) {
			return;
		}
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();

			if ((resource != null) && (!resource.isEmpty())) {
				copyResource(plugin.getResource(resource), file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void prepareFile(String filePath) {
		prepareFile(filePath, null);
	}

	public void setHeader(File file, String[] header) {
		if (!file.exists()) {
			return;
		}

		try {
			StringBuilder config = new StringBuilder("");
			BufferedReader reader = new BufferedReader(new java.io.FileReader(
					file));
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				config.append(currentLine + "\n");
			}

			reader.close();
			config.append("# +----------------------------------------------------+ #\n");

			for (String line : header) {
				if (line.length() <= 50) {

					int lenght = (50 - line.length()) / 2;
					StringBuilder finalLine = new StringBuilder(line);

					for (int i = 0; i < lenght; i++) {
						finalLine.append(" ");
						finalLine.reverse();
						finalLine.append(" ");
						finalLine.reverse();
					}

					if (line.length() % 2 != 0) {
						finalLine.append(" ");
					}

					config.append("# < " + finalLine.toString() + " > #\n");
				}
			}

			config.append("# +----------------------------------------------------+ #");

			BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(
					file));
			writer.write(prepareConfigString(config.toString()));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int getCommentsNum(File file) {
		if (!file.exists()) {
			return 0;
		}
		try {
			int comments = 0;

			BufferedReader reader = new BufferedReader(new java.io.FileReader(
					file));
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				if (currentLine.startsWith("#")) {
					comments++;
				}
			}

			reader.close();
			return comments;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private String prepareConfigString(String configString) {
		int lastLine = 0;
		int headerLine = 0;

		String[] lines = configString.split("\n");
		StringBuilder config = new StringBuilder("");

		for (String line : lines) {
			if (line.startsWith(getPluginName() + "_COMMENT")) {
				String comment = "#"
						+ line.trim().substring(line.indexOf(":") + 1);

				if (comment.startsWith("# +-")) {

					if (headerLine == 0) {
						config.append(comment + "\n");

						lastLine = 0;
						headerLine = 1;
					} else if (headerLine == 1) {
						config.append(comment + "\n\n");

						lastLine = 0;
						headerLine = 0;
					}
				} else {
					String normalComment;
					
					if (comment.startsWith("# ' ")) {
						normalComment = comment.substring(0,
								comment.length() - 1)
								.replaceFirst("# ' ", "# ");
					} else {
						normalComment = comment;
					}

					if (lastLine == 0) {
						config.append(normalComment + "\n");
					} else if (lastLine == 1) {
						config.append("\n" + normalComment + "\n");
					}

					lastLine = 0;
				}
			} else {
				config.append(line + "\n");
				lastLine = 1;
			}
		}

		return config.toString();
	}

	public void saveConfig(String configString, File file) {
		String configuration = prepareConfigString(configString);
		try {
			BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(
					file));
			writer.write(configuration);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPluginName() {
		return plugin.getDescription().getName();
	}

	private void copyResource(InputStream resource, File file) {
		try {
			java.io.OutputStream out = new java.io.FileOutputStream(file);

			byte[] buf = new byte['?'];
			int length;
			while ((length = resource.read(buf)) > 0) {
				out.write(buf, 0, length);
			}

			out.close();
			resource.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}