package uk.co.threebugs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.co.threebugs.service.DataSyncService;
import uk.co.threebugs.service.DataSyncServiceImpl;

@SpringBootApplication
public class TickDataApplication implements CommandLineRunner{


    @Autowired
    private DataSyncService dataSyncService;


    public static void main(String[] args) {
        SpringApplication.run(TickDataApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        dataSyncService.refresh();
    }
}
