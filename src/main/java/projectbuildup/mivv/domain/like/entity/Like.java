package projectbuildup.mivv.domain.like.entity;

import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;

import java.util.ArrayList;
import java.util.List;

public class Like {
    private Long id;
    private User user;
    private List<WorthyConsumption> worthyConsumptions = new ArrayList<>();
    // 숏츠 추가해야함
    //private List<WorthyConsumption> worthyConsumptions = new ArrayList<>();

}
