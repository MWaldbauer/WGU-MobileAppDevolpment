package com.example.mwaldbauerscheduler.Database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.mwaldbauerscheduler.DAO.AssessmentDao;
import com.example.mwaldbauerscheduler.DAO.AssessmentDao_Impl;
import com.example.mwaldbauerscheduler.DAO.CourseDao;
import com.example.mwaldbauerscheduler.DAO.CourseDao_Impl;
import com.example.mwaldbauerscheduler.DAO.TermDao;
import com.example.mwaldbauerscheduler.DAO.TermDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SchedulerRoomDatabase_Impl extends SchedulerRoomDatabase {
  private volatile TermDao _termDao;

  private volatile CourseDao _courseDao;

  private volatile AssessmentDao _assessmentDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(7) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `term_table` (`termID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `term` TEXT NOT NULL, `startDate` INTEGER NOT NULL, `stopDate` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `course_table` (`courseID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `course` TEXT NOT NULL, `startDate` INTEGER NOT NULL, `startDateAlarm` INTEGER NOT NULL DEFAULT false, `stopDate` INTEGER NOT NULL, `stopDateAlarm` INTEGER NOT NULL DEFAULT false, `completion_status` TEXT NOT NULL DEFAULT 'Plan to Take', `termID` INTEGER NOT NULL, `mentor` TEXT, `mentor_phone` TEXT, `mentor_email` TEXT, `notes` TEXT, FOREIGN KEY(`termID`) REFERENCES `term_table`(`termID`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `assessment_table` (`assessmentID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `assessment` TEXT NOT NULL, `goalDate` INTEGER NOT NULL, `goalDateAlarm` INTEGER NOT NULL DEFAULT false, `stopDate` INTEGER NOT NULL, `notes` TEXT, `courseID` INTEGER NOT NULL, FOREIGN KEY(`courseID`) REFERENCES `course_table`(`courseID`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3129d0a0adae03164dba955076720165')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `term_table`");
        _db.execSQL("DROP TABLE IF EXISTS `course_table`");
        _db.execSQL("DROP TABLE IF EXISTS `assessment_table`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTermTable = new HashMap<String, TableInfo.Column>(4);
        _columnsTermTable.put("termID", new TableInfo.Column("termID", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTermTable.put("term", new TableInfo.Column("term", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTermTable.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTermTable.put("stopDate", new TableInfo.Column("stopDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTermTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTermTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTermTable = new TableInfo("term_table", _columnsTermTable, _foreignKeysTermTable, _indicesTermTable);
        final TableInfo _existingTermTable = TableInfo.read(_db, "term_table");
        if (! _infoTermTable.equals(_existingTermTable)) {
          return new RoomOpenHelper.ValidationResult(false, "term_table(com.example.mwaldbauerscheduler.Entities.Term).\n"
                  + " Expected:\n" + _infoTermTable + "\n"
                  + " Found:\n" + _existingTermTable);
        }
        final HashMap<String, TableInfo.Column> _columnsCourseTable = new HashMap<String, TableInfo.Column>(12);
        _columnsCourseTable.put("courseID", new TableInfo.Column("courseID", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseTable.put("course", new TableInfo.Column("course", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseTable.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseTable.put("startDateAlarm", new TableInfo.Column("startDateAlarm", "INTEGER", true, 0, "false", TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseTable.put("stopDate", new TableInfo.Column("stopDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseTable.put("stopDateAlarm", new TableInfo.Column("stopDateAlarm", "INTEGER", true, 0, "false", TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseTable.put("completion_status", new TableInfo.Column("completion_status", "TEXT", true, 0, "'Plan to Take'", TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseTable.put("termID", new TableInfo.Column("termID", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseTable.put("mentor", new TableInfo.Column("mentor", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseTable.put("mentor_phone", new TableInfo.Column("mentor_phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseTable.put("mentor_email", new TableInfo.Column("mentor_email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseTable.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCourseTable = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCourseTable.add(new TableInfo.ForeignKey("term_table", "NO ACTION", "NO ACTION",Arrays.asList("termID"), Arrays.asList("termID")));
        final HashSet<TableInfo.Index> _indicesCourseTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCourseTable = new TableInfo("course_table", _columnsCourseTable, _foreignKeysCourseTable, _indicesCourseTable);
        final TableInfo _existingCourseTable = TableInfo.read(_db, "course_table");
        if (! _infoCourseTable.equals(_existingCourseTable)) {
          return new RoomOpenHelper.ValidationResult(false, "course_table(com.example.mwaldbauerscheduler.Entities.Course).\n"
                  + " Expected:\n" + _infoCourseTable + "\n"
                  + " Found:\n" + _existingCourseTable);
        }
        final HashMap<String, TableInfo.Column> _columnsAssessmentTable = new HashMap<String, TableInfo.Column>(7);
        _columnsAssessmentTable.put("assessmentID", new TableInfo.Column("assessmentID", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssessmentTable.put("assessment", new TableInfo.Column("assessment", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssessmentTable.put("goalDate", new TableInfo.Column("goalDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssessmentTable.put("goalDateAlarm", new TableInfo.Column("goalDateAlarm", "INTEGER", true, 0, "false", TableInfo.CREATED_FROM_ENTITY));
        _columnsAssessmentTable.put("stopDate", new TableInfo.Column("stopDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssessmentTable.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssessmentTable.put("courseID", new TableInfo.Column("courseID", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAssessmentTable = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysAssessmentTable.add(new TableInfo.ForeignKey("course_table", "NO ACTION", "NO ACTION",Arrays.asList("courseID"), Arrays.asList("courseID")));
        final HashSet<TableInfo.Index> _indicesAssessmentTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAssessmentTable = new TableInfo("assessment_table", _columnsAssessmentTable, _foreignKeysAssessmentTable, _indicesAssessmentTable);
        final TableInfo _existingAssessmentTable = TableInfo.read(_db, "assessment_table");
        if (! _infoAssessmentTable.equals(_existingAssessmentTable)) {
          return new RoomOpenHelper.ValidationResult(false, "assessment_table(com.example.mwaldbauerscheduler.Entities.Assessment).\n"
                  + " Expected:\n" + _infoAssessmentTable + "\n"
                  + " Found:\n" + _existingAssessmentTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "3129d0a0adae03164dba955076720165", "5b8fd195ed41e6fcccabe67742190862");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "term_table","course_table","assessment_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `assessment_table`");
      _db.execSQL("DELETE FROM `course_table`");
      _db.execSQL("DELETE FROM `term_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public TermDao termDao() {
    if (_termDao != null) {
      return _termDao;
    } else {
      synchronized(this) {
        if(_termDao == null) {
          _termDao = new TermDao_Impl(this);
        }
        return _termDao;
      }
    }
  }

  @Override
  public CourseDao courseDao() {
    if (_courseDao != null) {
      return _courseDao;
    } else {
      synchronized(this) {
        if(_courseDao == null) {
          _courseDao = new CourseDao_Impl(this);
        }
        return _courseDao;
      }
    }
  }

  @Override
  public AssessmentDao assessmentDao() {
    if (_assessmentDao != null) {
      return _assessmentDao;
    } else {
      synchronized(this) {
        if(_assessmentDao == null) {
          _assessmentDao = new AssessmentDao_Impl(this);
        }
        return _assessmentDao;
      }
    }
  }
}
