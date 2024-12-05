package kr.ac.changchang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // 특정 유저 칭호 변경 (PUT)
    @PUT("api/character_status/{userId}/title/{titleId}")
    Call<Void> updateUserTitle(
            @Path("userId") int userId,
            @Path("titleId") int titleId
    );

    // 유저의 과제 추가 (POST)
    @POST("assignments/add")
    Call<Void> addAssignment(@Body Todo_assignmentRequest assignmentRequest);

    // 유저의 과제 제출 조회 (GET)
    @GET("assignments/{studentId}")
    Call<List<Todo_assignmentRespones>> getAssignments(@Path("studentId") int studentId);

    // 유저 정보 조회 (GET)
    @GET("api/users/{userId}/character_status")
    Call<UserStatusResponse> getUserStatus(@Path("userId") int userId);
}