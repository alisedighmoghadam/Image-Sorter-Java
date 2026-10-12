<!-- Java -->

# Sorter – Image Sorter

A JavaFX desktop app that organises a folder of photos by their dimensions, either into **wide / portrait / square** folders or into one folder per exact resolution.

> **Project status: not completed.** Both sorting modes work, but the app was left as a beta: it only handles JPEG files and was never packaged as a release.

![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-17-007396)

![Screenshot 1](https://github.com/alisedighmoghadam/Image-Sorter-Java/blob/main/screenshots/1.png?raw=true)
![Screenshot 2](https://github.com/alisedighmoghadam/Image-Sorter-Java/blob/main/screenshots/2.png?raw=true)

## Why

When you download a batch of wallpapers (for example from Unsplash) for both a laptop and a phone, portrait and landscape images end up mixed together. The same happens after a product photoshoot when you need square shots for posts and tall shots for stories. Sorting them by hand is slow, so Sorter does it for you.

## Features

- **Two sorting modes**
  - *Horizontal & vertical sort* – copies each image into `wide_images/`, `portrait_images/` or `square_images/`.
  - *Dimensional sort* – creates one folder per resolution, e.g. `1920 X 1080/`.
- **Multithreaded** – files are split into batches of 100 and processed in parallel with a fixed thread pool, keeping the UI responsive.
- **Safe by default** – images are copied, not moved. After sorting, you can choose to send the originals to the Recycle Bin / Trash.
- **Input validation** – clear error dialogs for invalid paths and folders with no supported images.
- Supported formats: JPEG / JPG.

## Tech stack

Java 17 · JavaFX 17 · `ImageIO` · `java.util.concurrent` (ExecutorService)

## Getting started

### Requirements

- JDK 17+
- [JavaFX SDK 17](https://openjfx.io/)

### Run in Eclipse (project files included)

1. **File → Import → Existing Projects into Workspace** and select this folder.
2. Add the JavaFX SDK as a user library named `FX17` (or update the build path).
3. Run `application.SorterUI`.

### Run from the command line

```bash
PATH_TO_FX=/path/to/javafx-sdk-17/lib
javac --module-path "$PATH_TO_FX" -d out $(find src -name "*.java")
cp src/application/application.css out/application/
java --module-path "$PATH_TO_FX:out" -m Sorter/application.SorterUI
```

## Usage

1. Enter the path of the folder that contains your images and click **Next**.
2. Pick *Horizontal and vertical sort* or *Dimensional sort* and click **Sort**.
3. When it finishes, choose whether to delete the unsorted originals.

## Known limitations

- Very large folders can use a lot of memory because every image is fully decoded to read its size.
- JPEG / JPG only.
