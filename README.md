# Hack

![Build](https://github.com/CompileBulai/Hack/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)

## Template ToDo list
- [x] Create a new [IntelliJ Platform Plugin Template][template] project.
- [ ] Get familiar with the [template documentation][template].
- [ ] Adjust the [pluginGroup](./gradle.properties) and [pluginName](./gradle.properties), as well as the [id](./src/main/resources/META-INF/plugin.xml) and [sources package](./src/main/kotlin).
- [ ] Adjust the plugin description in `README` (see [Tips][docs:plugin-description])
- [ ] Review the [Legal Agreements](https://plugins.jetbrains.com/docs/marketplace/legal-agreements.html?from=IJPluginTemplate).
- [ ] [Publish a plugin manually](https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html?from=IJPluginTemplate) for the first time.
- [ ] Set the `MARKETPLACE_ID` in the above README badges. You can obtain it once the plugin is published to JetBrains Marketplace.
- [ ] Set the [Plugin Signing](https://plugins.jetbrains.com/docs/intellij/plugin-signing.html?from=IJPluginTemplate) related [secrets](https://github.com/JetBrains/intellij-platform-plugin-template#environment-variables).
- [ ] Set the [Deployment Token](https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html?from=IJPluginTemplate).
- [ ] Click the <kbd>Watch</kbd> button on the top of the [IntelliJ Platform Plugin Template][template] to be notified about releases containing new features and fixes.

<!-- Plugin description -->
This Fancy IntelliJ Platform Plugin is going to be your implementation of the brilliant ideas that you have.

This specific section is a source for the [plugin.xml](/src/main/resources/META-INF/plugin.xml) file which will be extracted by the [Gradle](/build.gradle.kts) during the build process.

To keep everything working, do not remove `<!-- ... -->` sections. 
<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Hack"</kbd> >
  <kbd>Install</kbd>
  
- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/CompileBulai/Hack/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation

---
# 🧠 Crispy – AI-Enhanced Project Task Manager for IntelliJ

**Crispy** is a smart, interactive, and AI-powered task management plugin designed to help developers organize, plan, and track tasks directly inside IntelliJ IDEA.

Whether you're starting a new project, breaking down a feature, or just keeping track of coding goals, Crispy helps you stay focused and efficient.

---

## 🚀 Features

- ✅ **To-Do List Tool Window** integrated in the IDE
- 🤖 **AI-powered task generator** using OpenAI
- 📝 **Editable notes** for each task
- 🔗 **Link code to task** and navigate directly
- 📍 **Persistent task storage per project**
- 🐱 **Cat GIF reward** when you complete all tasks
- 📊 **Progress bar tracking completion**
- 🧠 **Smart duplicate detection**
- 🔒 **Project-level state saved in XML**

---

## 🔧 How It Works

1. On first plugin load, Crispy asks:  
   _“What project do you want to implement?”_

2. You enter a short description — e.g.  
   _“A weather app with a REST API and UI”_

3. Then, you choose:
   - **Short tasks** ("Build UI", "Connect API")
   - or **Detailed tasks** ("Design weather forecast screen", etc.)

4. Tasks are generated via **OpenAI** and added directly to the list.

5. For each task, you can:
   - Add/edit notes (`📝 Note` button)
   - Link selected code from the editor (`Right click → Associate Code with Task`)
   - Jump to code location (`GoTo Code` button)

---

## 🖼️ UI Overview

| Feature | Screenshot |
|--------|------------|
| To-Do List + Notes + Code Link | ![Crispy Plugin Screenshot](./demo_ui.gif) |
| Task Completion + Progress Bar | ✅ |
| Cat GIF when 100% done | 🐱 |

---

## 📁 File Structure

- `ToDoList.kt` – Main tool window logic
- `Task.kt` – Data model for tasks
- `ToDoListService.kt` – Persistence and storage logic
- `TaskWithNotePanel.kt` – UI component per task
- `plugin.xml` – Plugin definition and extensions
- `TaskGenerator.kt` – AI integration via OpenAI API
- `/cat/cat.gif` – The reward 🐱

---

## 🧠 Powered by AI

Crispy uses OpenAI's ChatGPT (via API) to:

- Transform project descriptions into action-oriented task lists
- Optionally generate short vs. detailed task styles
- Enhance productivity through automatic breakdown

> 🔐 The OpenAI API key is securely used and never exposed in the UI.

---

## ⚙️ Installation & Setup

1. Clone the repo:
   ```bash
   git clone https://github.com/AndreiBug/HackItAll.git
   cd HackItAll
