package controllers;

import javafx.event.Event;

public abstract class DatabaseViewingWindowController extends WindowController {

	public abstract void addingEntry();

	public abstract void editingEntry(Event event);

}
