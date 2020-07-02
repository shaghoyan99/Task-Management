package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class Comment {

    private int id;
    private int task_id;
    private int user_id;
    private String comment;
    private Date date;
    private Task task;
    private User user;

}
