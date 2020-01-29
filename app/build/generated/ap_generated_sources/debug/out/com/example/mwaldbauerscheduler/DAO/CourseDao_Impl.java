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
public final class CourseDao_Impl implements CourseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Course> __insertionAdapterOfCourse;

  private final EntityDeletionOrUpdateAdapter<Course> __deletionAdapterOfCourse;

  private final EntityDeletionOrUpdateAdapter<Course> __updateAdapterOfCourse;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCoursesTable;

  public CourseDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCourse = new EntityInsertionAdapter<Course>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `course_table` (`courseID`,`course`,`startDate`,`startDateAlarm`,`stopDate`,`stopDateAlarm`,`completion_status`,`termID`,`mentor`,`mentor_phone`,`mentor_email`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Course value) {
        stmt.bindLong(1, value.getCourseID());
        if (value.getCourse() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCourse());
        }
        final Long _tmp;
        _tmp = Converters.dateToTimestamp(value.getStartDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        final Integer _tmp_1;
        _tmp_1 = value.getStartDateAlarm() == null ? null : (value.getStartDateAlarm() ? 1 : 0);
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
        final Integer _tmp_3;
        _tmp_3 = value.getStopDateAlarm() == null ? null : (value.getStopDateAlarm() ? 1 : 0);
        if (_tmp_3 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, _tmp_3);
        }
        if (value.getCompletionStatus() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCompletionStatus());
        }
        stmt.bindLong(8, value.getTermID());
        if (value.getMentor() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getMentor());
        }
        if (value.getMentorPhone() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getMentorPhone());
        }
        if (value.getMentorEmail() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getMentorEmail());
        }
        if (value.getNotes() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getNotes());
        }
      }
    };
    this.__deletionAdapterOfCourse = new EntityDeletionOrUpdateAdapter<Course>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `course_table` WHERE `courseID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Course value) {
        stmt.bindLong(1, value.getCourseID());
      }
    };
    this.__updateAdapterOfCourse = new EntityDeletionOrUpdateAdapter<Course>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `course_table` SET `courseID` = ?,`course` = ?,`startDate` = ?,`startDateAlarm` = ?,`stopDate` = ?,`stopDateAlarm` = ?,`completion_status` = ?,`termID` = ?,`mentor` = ?,`mentor_phone` = ?,`mentor_email` = ?,`notes` = ? WHERE `courseID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Course value) {
        stmt.bindLong(1, value.getCourseID());
        if (value.getCourse() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCourse());
        }
        final Long _tmp;
        _tmp = Converters.dateToTimestamp(value.getStartDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        final Integer _tmp_1;
        _tmp_1 = value.getStartDateAlarm() == null ? null : (value.getStartDateAlarm() ? 1 : 0);
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
        final Integer _tmp_3;
        _tmp_3 = value.getStopDateAlarm() == null ? null : (value.getStopDateAlarm() ? 1 : 0);
        if (_tmp_3 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, _tmp_3);
        }
        if (value.getCompletionStatus() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCompletionStatus());
        }
        stmt.bindLong(8, value.getTermID());
        if (value.getMentor() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getMentor());
        }
        if (value.getMentorPhone() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getMentorPhone());
        }
        if (value.getMentorEmail() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getMentorEmail());
        }
        if (value.getNotes() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getNotes());
        }
        stmt.bindLong(13, value.getCourseID());
      }
    };
    this.__preparedStmtOfDeleteCoursesTable = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM course_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Course obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCourse.insert(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Course obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCourse.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Course obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCourse.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCoursesTable() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCoursesTable.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteCoursesTable.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Course>> getCourses() {
    final String _sql = "SELECT * FROM course_table ORDER BY startDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
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
  public LiveData<List<Course>> getAllCoursesByTermIDLive(final int termID) {
    final String _sql = "SELECT * FROM course_table WHERE termID = ? ORDER BY startDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, termID);
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
  public List<Course> getAllCoursesByTermID(final int termID) {
    final String _sql = "SELECT * FROM course_table WHERE termID = ? ORDER BY startDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, termID);
    __db.assertNotSuspendingTransaction();
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
      _statement.release();
    }
  }

  @Override
  public Course getCourseByID(final int courseID) {
    final String _sql = "SELECT * FROM course_table WHERE courseID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, courseID);
    __db.assertNotSuspendingTransaction();
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
      final Course _result;
      if(_cursor.moveToFirst()) {
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
        _result = new Course(_tmpCourse,_tmpStartDate,_tmpStartDateAlarm,_tmpStopDate,_tmpStopDateAlarm,_tmpCompletionStatus,_tmpTermID,_tmpMentor,_tmpMentorPhone,_tmpMentorEmail,_tmpNotes);
        final int _tmpCourseID;
        _tmpCourseID = _cursor.getInt(_cursorIndexOfCourseID);
        _result.setCourseID(_tmpCourseID);
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
