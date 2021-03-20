import React, { useEffect, useState } from 'react';
import { Divider, List } from 'semantic-ui-react';
import DataManager from '../../data/DataManager';

import './community.css';

const CommunityPage = () => {
  const [communities, setCommunities] = useState([]);
  useEffect(() => {
    const fetchData = async () => {
      const data = await DataManager.getAllCommunities();
      setCommunities(data);
    };
    fetchData();
  }, []);

  return (
    <div>
      <h1>List of Communities</h1>
      <Divider />
      <div className="community-content">
        <List>
          {communities.map((community) => (
            <List.Item key={community.id}>
              <List.Icon name="newspaper outline" />
              <List.Content>
                <List.Header as="a">{community.name}</List.Header>
                <List.Description>{community.description}</List.Description>
              </List.Content>
            </List.Item>
          ))}
        </List>
      </div>
    </div>
  );
};

export default CommunityPage;
