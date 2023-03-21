package projectbuildup.mivv.domain.challenge.dto.request;

import projectbuildup.mivv.domain.challenge.entity.Challenge;

import java.util.List;

public class ChallengeRequestDto {
    public static class CreationRequest{
        String mainTitle;
        String subTitle;
        List<String> Content;


        public Challenge toEntity{
            //return Challenge;
        }

    }
    public static class UpdateRequest {



    }
    public static class ReadRequest {


    }
    public static class DeleteRequest{


    }
}
