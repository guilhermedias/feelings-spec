package me.espere.feelings.spec.dictionary;

import java.util.Optional;

public interface VadDictionary {
    Optional<VadEntry> getEntry(String word);
}
