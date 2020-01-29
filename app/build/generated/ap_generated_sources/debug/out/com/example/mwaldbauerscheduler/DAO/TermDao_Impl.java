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
import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.Entities.Term;
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
public final class TermDao_Impl implements TermDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Term> __insertionAdapterOfTerm;

  private final EntityDeletionOrUpdateAdapter<Term> __deletionAdapterOfTerm;

  private final EntityDeletionOrUpdateAdapter<Term> __updateAdapterOfTerm;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTermTable;

  public TermDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTerm = new EntityInsertionAdapter<Term>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `term_table` (`termID`,`term`,`startDate`,`stopDate`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Term value) {
        stmt.bindLong(1, value.getTermID());
        if (value.getTerm() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTerm());
        }
        final Long _tmp;
        _tmp = Converters.dateToTimestamp(value.getStartDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        final Long _tmp_1;
        _tmp_1 = Converters.dateToTimestamp(value.getStopDate());
        if (_tmp_1 == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfTerm = new EntityDeletionOrUpdateAdapter<Term>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `term_table` WHERE `termID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Term value) {
        stmt.bindLong(1, value.getTermID());
      }
    };
    this.__updateAdapterOfTerm = new EntityDeletionOrUpdateAdapter<Term>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `term_table` SET `termID` = ?,`term` = ?,`startDate` = ?,`stopDate` = ? WHERE `termID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Term value) {
        stmt.bindLong(1, value.getTermID());
        if (value.getTerm() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTerm());
        }
        final Long _tmp;
        _tmp = Converters.dateToTimestamp(value.getStartDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        final Long _tmp_1;
        _tmp_1 = Converters.dateToTimestamp(value.getStopDate());
        if (_tmp_1 == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp_1);
        }
        stmt.bindLong(5, value.getTermID());
      }
    };
    this.__preparedStmtOfDeleteTermTable = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM term_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Term obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTerm.insert(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Term obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTerm.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Term obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTerm.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteTermTable() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTermTable.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteTermTable.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Term>> getAllTerms() {
    final String _sql = "SELECT * FROM term_table ORDER BY startDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"term_table"}, false, new Callable<List<Term>>() {
      @Override
      public List<Term> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTermID = CursorUtil.getColumnIndexOrThrow(_cursor, "termID");
          final int _cursorIndexOfTerm = CursorUtil.getColumnIndexOrThrow(_cursor, "term");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfStopDate = CursorUtil.getColumnIndexOrThrow(_cursor, "stopDate");
          final List<Term> _result = new ArrayList<Term>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Term _item;
            final String _tmpTerm;
            _tmpTerm = _cursor.getString(_cursorIndexOfTerm);
            final Date _tmpStartDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartDate);
            }
            _tmpStartDate = Converters.fromTimestamp(_tmp);
            final Date _tmpStopDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStopDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfStopDate);
            }
            _tmpStopDate = Converters.fromTimestamp(_tmp_1);
            _item = new Term(_tmpTerm,_tmpStartDate,_tmpStopDate);
            final int _tmpTermID;
            _tmpTermID = _cursor.getInt(_cursorIndexOfTermID);
            _item.setTermID(_tmpTermID);
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
  public LiveData<List<Course>> getCoursesAttachedToTerm(final String term) {
    final String _sql = "SELECT * FROM course_table WHERE course = ? ORDER BY startDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (term == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, term);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"course_table"}, false, new Callable<List<Course>>() {
      @Override
      public List<Course> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCourseID = CursorUtil.getColumnIndexOrThrow(_cursor, "courseID");
          final int _cursorIndexOfCourse = CursorUtil.getColumnIndexOrThrow(_cursor, "course");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfStartDateAlarm = CursorUtil.getColumnIndexOrThrow(_cursor, "startDateAlarm");
          final int _cursorIndexOfStopDate = CursorUtil.getColumnIndexOrThrow(_cursor, "stopDate");
          final int _cursorIndexOfStopDateAlarm = CursorUtil.getColumnIndexOrThrow(_cursor, "stopDateAlarm");
          final int _cursorIndexOfCompletionStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "completion_status");
          final int _cursorIndexOfTermID = CursorUtil.getColumnIndexOrThrow(_cursor, "termID");
          final int _cursorIndexOfMentor = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor");
          final int _cursorIndexOfMentorPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_phone");
          final int _cursorIndexOfMentorEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_email");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Course> _result = new ArrayList<Course>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Course _item;
            final String _tmpCourse;
            _tmpCourse = _cursor.getString(_cursorIndexOfCourse);
            final Date _tmpStartDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartDate);
            }
            _tmpStartDate = Converters.fromTimestamp(_tmp);
            final Boolean _tmpStartDateAlarm;
            final Integer _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStartDateAlarm)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(_cursorIndexOfStartDateAlarm);
            }
            _tmpStartDateAlarm = _tmp_1 == null ? null : _tmp_1 != 0;
            final Date _tmpStopDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStopDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfStopDate);
            }
            _tmpStopDate = Converters.fromTimestamp(_tmp_2);
            final Boolean _tmpStopDateAlarm;
            final Integer _tmp_3;
            if (_cursor.isNull(_cursorIndexOfStopDateAlarm)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getInt(_cursorIndexOfStopDateAlarm);
            }
            _tmpStopDateAlarm = _tmp_3 == null ? null : _tmp_3 != 0;
            final String _tmpCompletionStatus;
            _tmpCompletionStatus = _cursor.getString(_cursorIndexOfCompletionStatus);
            final int _tmpTermID;
            _tmpTermID = _cursor.getInt(_cursorIndexOfTermID);
            final String _tmpMentor;
            _tmpMentor = _cursor.getString(_cursorIndexOfMentor);
            final String _tmpMentorPhone;
            _tmpMentorPhone = _cursor.getString(_cursorIndexOfMentorPhone);
            final String _tmpMentorEmail;
            _tmpMentorEmail = _cursor.getString(_cursorIndexOfMentorEmail);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new Course(_tmpCourse,_tmpStartDate,_tmpStartDateAlarm,_tmpStopDate,_tmpStopDateAlarm,_tmpCompletionStatus,_tmpTermID,_tmpMentor,_tmpMentorPhone,_tmpMentorEmail,_tmpNotes);
            final int _tmpCourseID;
            _tmpCourseID = _cursor.getInt(_cursorIndexOfCourseID);
            _item.setCourseID(_tmpCourseID);
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
  public Term getTermByID(final int termID) {
    final String _sql = "SELECT * FROM term_table WHERE termID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, termID);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTermID = CursorUtil.getColumnIndexOrThrow(_cursor, "termID");
      final int _cursorIndexOfTerm = CursorUtil.getColumnIndexOrThrow(_cursor, "term");
      final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
      final int _cursorIndexOfStopDate = CursorUtil.getColumnIndexOrThrow(_cursor, "stopDate");
      final Term _result;
      if(_cursor.moveToFirst()) {
        final String _tmpTerm;
        _tmpTerm = _cursor.getString(_cursorIndexOfTerm);
        final Date _tmpStartDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfStartDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfStartDate);
        }
        _tmpStartDate = Converters.fromTimestamp(_tmp);
        final Date _tmpStopDate;
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfStopDate)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfStopDate);
        }
        _tmpStopDate = Converters.fromTimestamp(_tmp_1);
        _result = new Term(_tmpTerm,_tmpStartDate,_tmpStopDate);
        final int _tmpTermID;
        _tmpTermID = _cursor.getInt(_cursorIndexOfTermID);
        _result.setTermID(_tmpTermID);
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
