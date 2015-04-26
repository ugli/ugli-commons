package se.ugli.commons;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public final class CloseCommand {

	public static void execute(final Object... objects) {
		for (final Object obj : objects)
			if (obj != null)
				try {
					if (obj instanceof Connection)
						((Connection) obj).close();
					else if (obj instanceof Statement)
						((Statement) obj).close();
					else if (obj instanceof ResultSet)
						((ResultSet) obj).close();
					// Java 7
					// else if (obj instanceof AutoCloseable)
					// ((AutoCloseable) obj).close();
					else if (obj instanceof Closeable)
						((Closeable) obj).close();
					else
						obj.getClass().getMethod("close").invoke(obj);
				} catch (final Exception e) {
					e.printStackTrace();
				}
	}

	private CloseCommand() {

	}

}
