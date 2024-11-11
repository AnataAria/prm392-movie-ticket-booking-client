    package com.theanimegroup.movie_ticket_booking_client.api;

    import com.theanimegroup.movie_ticket_booking_client.models.entity.Seat;
    import com.theanimegroup.movie_ticket_booking_client.models.entity.Ticket;
    import com.theanimegroup.movie_ticket_booking_client.models.entity.TicketDto;
    import com.theanimegroup.movie_ticket_booking_client.models.request.TicketPurchaseRequest;
    import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;
    import com.theanimegroup.movie_ticket_booking_client.models.response.TicketResponse;

    import java.util.List;

    import retrofit2.Call;
    import retrofit2.http.Body;
    import retrofit2.http.GET;
    import retrofit2.http.Header;
    import retrofit2.http.POST;
    import retrofit2.http.Path;

    public interface TicketService {
        @POST("/api/2024-11-11/solvedtickets/tickets")
        Call<ResponseObject<TicketResponse>> purchaseTicket(@Header("Authorization") String token,@Body TicketPurchaseRequest request);

        @GET("/api/2024-11-11/tickets/movieid/{movieId}")
        Call<ResponseObject<List<Ticket>>> getTicketByMovieId(@Path("movieId") int movieId);

        @GET("/api/2024-11-11/tickets/{id}")
        Call<ResponseObject<Ticket>> getTicketById(@Path("id") int id);
    }

