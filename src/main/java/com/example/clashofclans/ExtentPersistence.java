package com.example.clashofclans;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class ExtentPersistence {

	private ExtentPersistence() {} // no instances

	public static <T extends Serializable> void saveExtent(List<T> extent, Path file) {
		try (var out = new ObjectOutputStream(Files.newOutputStream(file))) {
			out.writeObject(extent);
		} catch (IOException e) {
			throw new RuntimeException("Could not save extent to file: " + file, e);
		}
	}


	public static <T extends Serializable> List<T> loadExtent(Path file) {
		if (!Files.exists(file)) return new java.util.ArrayList<>();

		try (var in = new ObjectInputStream(Files.newInputStream(file))) {
			return (List<T>) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException("Could not load extent from file: " + file, e);
		}
	}
	public static void deleteExtent(Path file) {
		try {
			if (Files.exists(file)) {
				Files.delete(file);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not delete extent file: " + file, e);
		}
	}
}
