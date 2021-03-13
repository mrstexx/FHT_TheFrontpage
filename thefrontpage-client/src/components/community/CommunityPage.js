import React from 'react';
import { Divider, List } from 'semantic-ui-react';

import './community.css';

const CommunityPage = () => {
  return (
    <div>
      <h1>List of Communities</h1>
      <Divider />
      <div className="community-content">
        <List>
          <List.Item>
            <List.Icon name="newspaper outline" />
            <List.Content>
              <List.Header as="a">programming</List.Header>
              <List.Description>
                Place for all software developers, coders, engineers...
              </List.Description>
            </List.Content>
          </List.Item>
          <List.Item>
            <List.Icon name="newspaper outline" />
            <List.Content>
              <List.Header as="a">design</List.Header>
              <List.Description>
                Place for all UI/UX designer...
              </List.Description>
            </List.Content>
          </List.Item>
        </List>
      </div>
    </div>
  );
};

export default CommunityPage;
