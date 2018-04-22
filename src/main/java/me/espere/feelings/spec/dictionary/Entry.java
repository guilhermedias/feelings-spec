package me.espere.feelings.spec.dictionary;

import lombok.Value;
import me.espere.feelings.spec.VadValue;

@Value
public class Entry {
    private String word;
    private VadValue vadValue;
}
