package net.sf.memoranda;

public interface NoteListener {
  void noteChange(Note note, boolean toSaveCurrentNote);
}
