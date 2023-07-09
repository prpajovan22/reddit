package com.ftn.reddit.Interface;


import com.ftn.reddit.model.Community;

import java.util.List;

public interface CommunityInterface {

    List<Community> findAll();

    Community save(Community post);

    Community findById(Integer id);

    void delete(Community post);
}
