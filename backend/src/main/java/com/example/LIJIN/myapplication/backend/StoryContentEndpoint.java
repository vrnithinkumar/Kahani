package com.example.LIJIN.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "storyContentApi",
        version = "v1",
        resource = "storyContent",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.LIJIN.example.com",
                ownerName = "backend.myapplication.LIJIN.example.com",
                packagePath = ""
        )
)
public class StoryContentEndpoint {

    private static final Logger logger = Logger.getLogger(StoryContentEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(StoryContent.class);
    }

    /**
     * Returns the {@link StoryContent} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code StoryContent} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "storyContent/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public StoryContent get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting StoryContent with ID: " + id);
        StoryContent storyContent = ofy().load().type(StoryContent.class).id(id).now();
        if (storyContent == null) {
            throw new NotFoundException("Could not find StoryContent with ID: " + id);
        }
        return storyContent;
    }

    /**
     * Inserts a new {@code StoryContent}.
     */
    @ApiMethod(
            name = "insert",
            path = "storyContent",
            httpMethod = ApiMethod.HttpMethod.POST)
    public StoryContent insert(StoryContent storyContent) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that storyContent.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(storyContent).now();
        logger.info("Created StoryContent with ID: " + storyContent.getId());

        return ofy().load().entity(storyContent).now();
    }

    /**
     * Updates an existing {@code StoryContent}.
     *
     * @param id           the ID of the entity to be updated
     * @param storyContent the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code StoryContent}
     */
    @ApiMethod(
            name = "update",
            path = "storyContent/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public StoryContent update(@Named("id") Long id, StoryContent storyContent) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(storyContent).now();
        logger.info("Updated StoryContent: " + storyContent);
        return ofy().load().entity(storyContent).now();
    }

    /**
     * Deletes the specified {@code StoryContent}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code StoryContent}
     */
    @ApiMethod(
            name = "remove",
            path = "storyContent/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(StoryContent.class).id(id).now();
        logger.info("Deleted StoryContent with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "storyContent",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<StoryContent> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<StoryContent> query = ofy().load().type(StoryContent.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<StoryContent> queryIterator = query.iterator();
        List<StoryContent> storyContentList = new ArrayList<StoryContent>(limit);
        while (queryIterator.hasNext()) {
            storyContentList.add(queryIterator.next());
        }
        return CollectionResponse.<StoryContent>builder().setItems(storyContentList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(StoryContent.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find StoryContent with ID: " + id);
        }
    }
}