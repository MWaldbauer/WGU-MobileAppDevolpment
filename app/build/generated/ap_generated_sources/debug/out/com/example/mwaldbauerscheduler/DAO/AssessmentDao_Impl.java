package com.example.mwaldbauerscheduler.DAO;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.mwaldbauerscheduler.Database.Converters;
import com.example.mwaldbauerscheduler.Entities.Assessment;
import java.lang.Boolean;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AssessmentDao_Impl implements AssessmentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Assessment> __insertionAdapterOfAssessment;

  private final EntityDeletionOrUpdateAdapter<Assessment> __deletionAdapterOfAssessment;

  private final EntityDeletionOrUpdateAdapter<Assessment> __updateAdapterOfAssessment;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAssessmentTable;

  public AssessmentDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAssessment = new EntityInsertionAdapter<Assessment>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `assessment_table` (`assessmentID`,`assessment`,`goalDate`,`goalDateAlarm`,`stopDate`,`notes`,`courseID`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Assessment value) {
        stmt.bindLong(1, value.getAssessmentID());
        if (value.getAssessment() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAssessment());
        }
        final Long _tmp;
        _tmp = Converters.dateToTimestamp(value.getGoalDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        final Integer _tmp_1;
        _tmp_1 = value.getGoalDateAlarm() == null ? null : (value.getGoalDateAlarm() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp_1);
        }
        final Long _tmp_2;
        _tmp_2 = Converters.dateToTimestamp(value.getStopDate());
        if (_tmp_2 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp_2);
        }
        if (value.getNotes() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getNotes());
        }
        stmt.bindLong(7, value.getCourseID());
      }
    };
    this.__deletionAdapterOfAssessment = new EntityDeletionOrUpdateAdapter<Assessment>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `assessment_table` WHERE `assessmentID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Assessment value) {
        stmt.bindLong(1, value.getAssessmentID());
      }
    };
    this.__updateAdapterOfAssessment = new EntityDeletionOrUpdateAdapter<Assessment>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `assessment_table` SET `assessmentID` = ?,`assessment` = ?,`goalDate` = ?,`goalDateAlarm` = ?,`stopDate` = ?,`notes` = ?,`courseID` = ? WHERE `assessmentID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Assessment value) {
        stmt.bindLong(1, value.getAssessmentID());
        if (value.getAssessment() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAssessment());
        }
        final Long _tmp;
        _tmp = Converters.dateToTimestamp(value.getGoalDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        final Integer _tmp_1;
        _tmp_1 = value.getGoalDateAlarm() == null ? null : (value.getGoalDateAlarm() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp_1);
        }
        final Long _tmp_2;
        _tmp_2 = Converters.dateToTimestamp(value.getStopDate());
        if (_tmp_2 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp_2);
        }
        if (value.getNotes() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getNotes());
        }
        stmt.bindLong(7, value.getCourseID());
        stmt.bindLong(8, value.getAssessmentID());
      }
    };
    this.__preparedStmtOfDeleteAssessmentTable = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM assessment_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Assessment obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAssessment.insert(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Assessment obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfAssessment.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Assessment obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfAssessment.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAssessmentTable() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAssessmentTable.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAssessmentTable.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Assessment>> getAssessments() {
    final String _sql = "SELECT * FROM assessment_table ORDER BY goalDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"assessment_table"}, false, new Callable<List<Assessment>>() {
      @Override
      public List<Assessment> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAssessmentID = CursorUtil.getColumnIndexOrThrow(_cursor, "assessmentID");
          final int _cursorIndexOfAssessment = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment");
          final int _cursorIndexOfGoalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "goalDate");
          final int _cursorIndexOfGoalDateAlarm = CursorUtil.getColumnIndexOrThrow(_cursor, "goalDateAlarm");
          final int _cursorIndexOfStopDate = CursorUtil.getColumnIndexOrThrow(_cursor, "stopDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCourseID = CursorUtil.getColumnIndexOrThrow(_cursor, "courseID");
          final List<Assessment> _result = new ArrayList<Assessment>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Assessment _item;
            final String _tmpAssessment;
            _tmpAssessment = _cursor.getString(_cursorIndexOfAssessment);
            final Date _tmpGoalDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfGoalDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfGoalDate);
            }
            _tmpGoalDate = Converters.fromTimestamp(_tmp);
            final Boolean _tmpGoalDateAlarm;
            final Integer _tmp_1;
            if (_cursor.isNull(_cursorIndexOfGoalDateAlarm)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(_cursorIndexOfGoalDateAlarm);
            }
            _tmpGoalDateAlarm = _tmp_1 == null ? null : _tmp_1 != 0;
            final Date _tmpStopDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStopDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfStopDate);
            }
            _tmpStopDate = Converters.fromTimestamp(_tmp_2);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final int _tmpCourseID;
            _tmpCourseID = _cursor.getInt(_cursorIndexOfCourseID);
            _item = new Assessment(_tmpAssessment,_tmpGoalDate,_tmpStopDate,_tmpGoalDateAlarm,_tmpNotes,_tmpCourseID);
            final int _tmpAssessmentID;
            _tmpAssessmentID = _cursor.getInt(_cursorIndexOfAssessmentID);
            _item.setAssessmentID(_tmpAssessmentID);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Assessment>> getAllAssessmentsByCourseIDLive(final int courseID) {
    final String _sql = "SELECT * FROM assessment_table WHERE courseID = ? ORDER BY goalDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, courseID);
    return __db.getInvalidationTracker().createLiveData(new String[]{"assessment_table"}, false, new Callable<List<Assessment>>() {
      @Override
      public List<Assessment> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAssessmentID = CursorUtil.getColumnIndexOrThrow(_cursor, "assessmentID");
          final int _cursorIndexOfAssessment = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment");
          final int _cursorIndexOfGoalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "goalDate");
          final int _cursorIndexOfGoalDateAlarm = CursorUtil.getColumnIndexOrThrow(_cursor, "goalDateAlarm");
          final int _cursorIndexOfStopDate = CursorUtil.getColumnIndexOrThrow(_cursor, "stopDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCourseID = CursorUtil.getColumnIndexOrThrow(_cursor, "courseID");
          final List<Assessment> _result = new ArrayList<Assessment>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Assessment _item;
            final String _tmpAssessment;
            _tmpAssessment = _cursor.getString(_cursorIndexOfAssessment);
            final Date _tmpGoalDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfGoalDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfGoalDate);
            }
            _tmpGoalDate = Converters.fromTimestamp(_tmp);
            final Boolean _tmpGoalDateAlarm;
            final Integer _tmp_1;
            if (_cursor.isNull(_cursorIndexOfGoalDateAlarm)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(_cursorIndexOfGoalDateAlarm);
            }
            _tmpGoalDateAlarm = _tmp_1 == null ? null : _tmp_1 != 0;
            final Date _tmpStopDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStopDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfStopDate);
            }
            _tmpStopDate = Converters.fromTimestamp(_tmp_2);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final int _tmpCourseID;
            _tmpCourseID = _cursor.getInt(_cursorIndexOfCourseID);
            _item = new Assessment(_tmpAssessment,_tmpGoalDate,_tmpStopDate,_tmpGoalDateAlarm,_tmpNotes,_tmpCourseID);
            final int _tmpAssessmentID;
            _tmpAssessmentID = _cursor.getInt(_cursorIndexOfAssessmentID);
            _item.setAssessmentID(_tmpAssessmentID);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<Assessment> getAllAssessmentsByCourseID(final int courseID) {
    final String _sql = "SELECT * FROM assessment_table WHERE courseID = ? ORDER BY goalDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, courseID);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfAssessmentID = CursorUtil.getColumnIndexOrThrow(_cursor, "assessmentID");
      final int _cursorIndexOfAssessment = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment");
      final int _cursorIndexOfGoalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "goalDate");
      final int _cursorIndexOfGoalDateAlarm = CursorUtil.getColumnIndexOrThrow(_cursor, "goalDateAlarm");
      final int _cursorIndexOfStopDate = CursorUtil.getColumnIndexOrThrow(_cursor, "stopDate");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final int _cursorIndexOfCourseID = CursorUtil.getColumnIndexOrThrow(_cursor, "courseID");
      final List<Assessment> _result = new ArrayList<Assessment>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Assessment _item;
        final String _tmpAssessment;
        _tmpAssessment = _cursor.getString(_cursorIndexOfAssessment);
        final Date _tmpGoalDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfGoalDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfGoalDate);
        }
        _tmpGoalDate = Converters.fromTimestamp(_tmp);
        final Boolean _tmpGoalDateAlarm;
        final Integer _tmp_1;
        if (_cursor.isNull(_cursorIndexOfGoalDateAlarm)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getInt(_cursorIndexOfGoalDateAlarm);
        }
        _tmpGoalDateAlarm = _tmp_1 == null ? null : _tmp_1 != 0;
        final Date _tmpStopDate;
        final Long _tmp_2;
        if (_cursor.isNull(_cursorIndexOfStopDate)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getLong(_cursorIndexOfStopDate);
        }
        _tmpStopDate = Converters.fromTimestamp(_tmp_2);
        final String _tmpNotes;
        _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        final int _tmpCourseID;
        _tmpCourseID = _cursor.getInt(_cursorIndexOfCourseID);
        _item = new Assessment(_tmpAssessment,_tmpGoalDate,_tmpStopDate,_tmpGoalDateAlarm,_tmpNotes,_tmpCourseID);
        final int _tmpAssessmentID;
        _tmpAssessmentID = _cursor.getInt(_cursorIndexOfAssessmentID);
        _item.setAssessmentID(_tmpAssessmentID);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Assessment getAssessmentByID(final int assessmentID) {
    final String _sql = "SELECT * FROM assessment_table WHERE assessmentID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, assessmentID);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfAssessmentID = CursorUtil.getColumnIndexOrThrow(_cursor, "assessmentID");
      final int _cursorIndexOfAssessment = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment");
      final int _cursorIndexOfGoalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "goalDate");
      final int _cursorIndexOfGoalDateAlarm = CursorUtil.getColumnIndexOrThrow(_cursor, "goalDateAlarm");
      final int _cursorIndexOfStopDate = CursorUtil.getColumnIndexOrThrow(_cursor, "stopDate");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final int _cursorIndexOfCourseID = CursorUtil.getColumnIndexOrThrow(_cursor, "courseID");
      final Assessment _result;
      if(_cursor.moveToFirst()) {
        final String _tmpAssessment;
        _tmpAssessment = _cursor.getString(_cursorIndexOfAssessment);
        final Date _tmpGoalDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfGoalDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfGoalDate);
        }
        _tmpGoalDate = Converters.fromTimestamp(_tmp);
        final Boolean _tmpGoalDateAlarm;
        final Integer _tmp_1;
        if (_cursor.isNull(_cursorIndexOfGoalDateAlarm)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getInt(_cursorIndexOfGoalDateAlarm);
        }
        _tmpGoalDateAlarm = _tmp_1 == null ? null : _tmp_1 != 0;
        final Date _tmpStopDate;
        final Long _tmp_2;
        if (_cursor.isNull(_cursorIndexOfStopDate)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getLong(_cursorIndexOfStopDate);
        }
        _tmpStopDate = Converters.fromTimestamp(_tmp_2);
        final String _tmpNotes;
        _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        final int _tmpCourseID;
        _tmpCourseID = _cursor.getInt(_cursorIndexOfCourseID);
        _result = new Assessment(_tmpAssessment,_tmpGoalDate,_tmpStopDate,_tmpGoalDateAlarm,_tmpNotes,_tmpCourseID);
        final int _tmpAssessmentID;
        _tmpAssessmentID = _cursor.getInt(_cursorIndexOfAssessmentID);
        _result.setAssessmentID(_tmpAssessmentID);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
