package ru.studyasm.storages;

import ru.studyasm.structures.Struct;

public interface Storage extends Struct {

    void setValue(int value);

    int getAddress();
}
