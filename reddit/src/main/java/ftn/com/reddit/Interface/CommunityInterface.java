package ftn.com.reddit.Interface;


import ftn.com.reddit.models.Community;

import java.util.List;

public interface CommunityInterface {

    List<Community> findAll();

    Community save(Community post);

    Community findById(Integer id);

    void delete(Community post);
}
