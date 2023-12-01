package care.up.auditing;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import org.springframework.data.auditing.DateTimeProvider;

/**
 * Custom DateTimeProvider implementation for Spring-Auditing to be able to use
 * Auditing-Annotations like '@lastModificationDate'
 */
public class DateTimeProviderImpl implements DateTimeProvider {

	@Override
	public Optional<TemporalAccessor> getNow() {
		System.out.println(OffsetDateTime.now());
		return Optional.of(OffsetDateTime.now());
	}
}
