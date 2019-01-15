package com.example.dialer;

import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;

abstract class GroupingListAdapter extends RecyclerView.Adapter {

    protected ContentObserver changeObserver =
            new ContentObserver(new Handler()) {
                @Override
                public boolean deliverSelfNotifications() {
                    return true;
                }

                @Override
                public void onChange(boolean selfChange) {
                    onContentChanged();
                }
            };
    protected DataSetObserver dataSetObserver =
            new DataSetObserver() {
                @Override
                public void onChanged() {
                    notifyDataSetChanged();
                }
            };
    private Cursor cursor;

    /**
     * 分组元数据，SparseIntArray可代替HashMap<Integer, Integer>提高性能
     * Key:该组所在List中的位置
     * Value:
     */
    private SparseIntArray groupMetadata;

    private int itemCount;

    public GroupingListAdapter() {
        reset();
    }

    /**
     * Finds all groups of adjacent items in the cursor and calls {@link #addGroup} for each of them.
     */
    protected abstract void addGroups(Cursor cursor);

    protected abstract void onContentChanged();

    public void changeCursor(Cursor cursor) {
        if (cursor == this.cursor) {
            return;
        }

        if (this.cursor != null) {
            this.cursor.unregisterContentObserver(changeObserver);
            this.cursor.unregisterDataSetObserver(dataSetObserver);
            this.cursor.close();
        }

        // Reset whenever the cursor is changed.
        reset();
        this.cursor = cursor;

        if (cursor != null) {
            addGroups(this.cursor);

            // Calculate the item count by subtracting group child counts from the cursor count.
            itemCount = groupMetadata.size();

            cursor.registerContentObserver(changeObserver);
            cursor.registerDataSetObserver(dataSetObserver);
            notifyDataSetChanged();
        }
    }

    /**
     * Records information about grouping in the list. Should be called by the overridden {@link
     * #addGroups} method.
     */
    public void addGroup(int cursorPosition, int groupSize) {
        int lastIndex = groupMetadata.size() - 1;
        if (lastIndex < 0 || cursorPosition <= groupMetadata.keyAt(lastIndex)) {
            groupMetadata.put(cursorPosition, groupSize);
        } else {
            // Optimization to avoid binary search if adding groups in ascending cursor position.
            groupMetadata.append(cursorPosition, groupSize);
        }
    }

    /**
     * Given the position of a list item, returns the size of the group of items corresponding to that
     * position.
     */
    public int getGroupSize(int listPosition) {
        if (listPosition < 0 || listPosition >= groupMetadata.size()) {
            return 0;
        }

        return groupMetadata.valueAt(listPosition);
    }

    private void reset() {
        itemCount = 0;
        groupMetadata = new SparseIntArray();
    }


    @Override
    public int getItemCount() {
        return itemCount;
    }

    /**
     * Given the position of a list item, returns the the first item in the group of items
     * corresponding to that position.
     */
    public Object getItem(int listPosition) {
        if (cursor == null || listPosition < 0 || listPosition >= groupMetadata.size()) {
            return null;
        }

        int cursorPosition = groupMetadata.keyAt(listPosition);
        if (cursor.moveToPosition(cursorPosition)) {
            return cursor;
        } else {
            return null;
        }
    }
}
