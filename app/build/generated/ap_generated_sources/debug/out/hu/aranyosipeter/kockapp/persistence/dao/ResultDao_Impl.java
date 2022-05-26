package hu.aranyosipeter.kockapp.persistence.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import hu.aranyosipeter.kockapp.persistence.entity.Result;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ResultDao_Impl implements ResultDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Result> __insertionAdapterOfResult;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ResultDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfResult = new EntityInsertionAdapter<Result>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Result` (`id`,`cube_size`,`time`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Result value) {
        stmt.bindLong(1, value.id);
        stmt.bindLong(2, value.cubeSize);
        stmt.bindLong(3, value.time);
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM result";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Result result) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfResult.insert(result);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<Result> getAllOfSize(final int cubeSize) {
    final String _sql = "SELECT * FROM result WHERE cube_size = (?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cubeSize);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCubeSize = CursorUtil.getColumnIndexOrThrow(_cursor, "cube_size");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final List<Result> _result = new ArrayList<Result>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Result _item;
        _item = new Result();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.cubeSize = _cursor.getInt(_cursorIndexOfCubeSize);
        _item.time = _cursor.getLong(_cursorIndexOfTime);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Result> getBestOfSize(final int cubeSize) {
    final String _sql = "SELECT * FROM result WHERE cube_size = (?) ORDER BY time ASC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cubeSize);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCubeSize = CursorUtil.getColumnIndexOrThrow(_cursor, "cube_size");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final List<Result> _result = new ArrayList<Result>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Result _item;
        _item = new Result();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.cubeSize = _cursor.getInt(_cursorIndexOfCubeSize);
        _item.time = _cursor.getLong(_cursorIndexOfTime);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
