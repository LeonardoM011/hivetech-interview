package hr.leonardom011.hivetechinterview;

import org.springframework.boot.SpringApplication;

public class TestHivetechInterviewApplication {

    public static void main(String[] args) {
        SpringApplication.from(HivetechInterviewApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
