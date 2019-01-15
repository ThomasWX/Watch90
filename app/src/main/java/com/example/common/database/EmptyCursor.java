package com.example.common.database;

import android.database.AbstractCursor;
import android.database.CursorIndexOutOfBoundsException;

/**
 * A cursor that is empty.
 *
 * <p>If you want an empty cursor, this class is better than a MatrixCursor because it has less
 * overhead.
 */
public final class EmptyCursor extends AbstractCursor {

    private String[] mColumns;

    public EmptyCursor(String[] columns) {
        this.mColumns = columns;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public String[] getColumnNames() {
        return mColumns;
    }

    @Override
    public String getString(int column) {
        throw cursorException();
    }

    @Override
    public short getShort(int column) {
        throw cursorException();
    }

    @Override
    public int getInt(int column) {
        throw cursorException();
    }

    @Override
    public long getLong(int column) {
        throw cursorException();
    }

    @Override
    public float getFloat(int column) {
        throw cursorException();
    }

    @Override
    public double getDouble(int column) {
        throw cursorException();
    }

    @Override
    public boolean isNull(int column) {
        throw cursorException();
    }

    private CursorIndexOutOfBoundsException cursorException() {
        return new CursorIndexOutOfBoundsException("Operation not permitted on an empty cursor.");
    }
}