package com.example.common.database;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * An {@AsyncQueryHandler} that will never return a null cursor.
 * Instead, will return a {@link Cursor} with 0 records.
 */
public abstract class NoNullCursorAsyncQueryHandler extends AsyncQueryHandler {

    public NoNullCursorAsyncQueryHandler(ContentResolver cr) {
        super(cr);
    }


    @Override
    public void startQuery(int token, Object cookie, Uri uri, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        final CookieWithProjection projectionCookie = new CookieWithProjection(cookie, projection);
        super.startQuery(token, projectionCookie, uri, projection, selection, selectionArgs, orderBy);

    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        CookieWithProjection projectionCookie = (CookieWithProjection) cookie;
        super.onQueryComplete(token, projectionCookie.originalCookie, cursor);
        if (cursor == null) {
            cursor = new EmptyCursor(projectionCookie.projection);
        }
        onNotNullableQueryComplete(token, projectionCookie.originalCookie, cursor);
    }

    protected abstract void onNotNullableQueryComplete(int token, Object cookie, Cursor cursor);

    /**
     * Class to add projection to an existing cookie.
     */
    private static class CookieWithProjection {

        public final Object originalCookie;
        public final String[] projection;

        public CookieWithProjection(Object cookie, String[] projection) {
            this.originalCookie = cookie;
            this.projection = projection;
        }
    }
}
