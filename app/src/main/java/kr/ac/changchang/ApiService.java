package kr.ac.changchang;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    @POST("api/assignment/add")
    Call<Void> addAssignment(@Body Todo_assignmentRequest assignmentRequest);

    // 유저의 과제 제출 조회 (GET)
    @GET("api/assignment/{studentId}")
    Call<List<Todo_assignmentRespones>> getAssignments(@Path("studentId") int studentId);

    // 유저 정보 조회 (GET)
    @GET("api/user/{userId}")
    Call<UserStatusResponse> getUserStatus(@Path("userId") int userId);

    // 유저 로그인 요청 (GET)
    @GET("api/user/{userId}")
    Call<UserStatusResponse> getUser(@Path("userId") int userId);

    // 공지 사항 정보 (GET)
    @GET("notices/department/{id}") //
    Call<List<MapNoticeResponse>> getMapNotices(@Path("id") int id);

    // 과목 정보 확인
    @GET("api/user/{userId}/subjects") // 엔드포인트는 서버에 따라 변경
    Call<List<Subject>> getSubjects(@Path("userId") int userId);

    // 과제 삭제
    @DELETE("api/todo/{todoId}")
    Call<Void> deleteTodo(@Path("todoId") int todoId);

    // 유저의 todo리스트 얻기
    @GET("api/todo/{userId}")
    Call<List<TodoResponse>> getTodos(@Path("userId") int userId);

    // 유저에 todo 추가
    @POST("api/todo/{studentId}")
    Call<ResponseBody> addTodo(
            @Path("studentId") int studentId,
            @Body TodoRequest todoRequest
    );

    // 유저의 타이틀 리스트 가져오기
    @GET("/api/character/{userId}/titles")
    Call<List<TitleResponse>> getTitles(@Path("userId") int userId);

    // 유저의 타이틀 정보 변경
    @PUT("api/character/{studentId}/title/{titleId}")
    Call<Void> changeUserTitle(
            @Path("studentId") int studentId,
            @Path("titleId") int titleId
    );
}