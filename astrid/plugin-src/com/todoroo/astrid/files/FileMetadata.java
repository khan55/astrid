package com.todoroo.astrid.files;

import com.todoroo.andlib.data.Property.LongProperty;
import com.todoroo.andlib.data.Property.StringProperty;
import com.todoroo.andlib.data.TodorooCursor;
import com.todoroo.andlib.sql.Criterion;
import com.todoroo.andlib.sql.Query;
import com.todoroo.astrid.core.PluginServices;
import com.todoroo.astrid.dao.MetadataDao.MetadataCriteria;
import com.todoroo.astrid.data.Metadata;

public class FileMetadata {

    /** metadata key */
    public static final String METADATA_KEY = "file"; //$NON-NLS-1$

    public static final String FILES_DIRECTORY = "attachments"; //$NON-NLS-1$

    /** Constants for file types */
    public static final String FILE_TYPE_AUDIO = "audio/"; //$NON-NLS-1$
    public static final String FILE_TYPE_IMAGE = "image/"; //$NON-NLS-1$



    public static final String FILE_TYPE_OTHER = "application/octet-stream";  //$NON-NLS-1$

    public static final StringProperty FILE_PATH = new StringProperty(Metadata.TABLE,
            Metadata.VALUE1.name);

    public static final StringProperty FILE_TYPE = new StringProperty(Metadata.TABLE,
            Metadata.VALUE2.name);

    public static final LongProperty DELETION_DATE = new LongProperty(Metadata.TABLE,
            Metadata.VALUE3.name);

    public static final LongProperty REMOTE_ID = new LongProperty(Metadata.TABLE,
            Metadata.VALUE4.name);

    public static final StringProperty URL = new StringProperty(Metadata.TABLE,
            Metadata.VALUE5.name);

    public static final StringProperty NAME = new StringProperty(Metadata.TABLE,
            Metadata.VALUE6.name);


    public static Metadata createNewFileMetadata(long taskId, String filePath, String fileName, String fileType) {
        Metadata metadata = new Metadata();
        metadata.setValue(Metadata.KEY, METADATA_KEY);
        metadata.setValue(Metadata.TASK, taskId);
        metadata.setValue(NAME, fileName);
        metadata.setValue(FILE_PATH, filePath);
        metadata.setValue(FILE_TYPE, fileType);
        metadata.setValue(REMOTE_ID, 0L);
        metadata.setValue(DELETION_DATE, 0L);
        return metadata;
    }

    public static boolean taskHasAttachments(long taskId) {
        TodorooCursor<Metadata> files = PluginServices.getMetadataService()
                .query(Query.select(Metadata.TASK).where(
                        Criterion.and(MetadataCriteria.byTaskAndwithKey(taskId, METADATA_KEY),
                                DELETION_DATE.eq(0))).limit(1));
        try {
            return files.getCount() > 0;
        } finally {
            files.close();
        }
    }

}
