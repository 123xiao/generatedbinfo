package tt.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTask {

	@Autowired
	private Actiontyx actiontyx;
	@Autowired
	private Actionyyw actionyyw;

	@Scheduled(cron = "0 0/5 * * * ? ")
	public void qunaertyxAction() {
		actiontyx.run();
	}

	@Scheduled(cron = "0 0/5 * * * ? ")
	public void qunaeryywAction() {
		actionyyw.run();
	}

}
