import { Link } from '@reach/router';
import React, { useEffect, useState } from 'react';
import {
  Button,
  Divider,
  Form,
  Input,
  List,
  Modal,
  TextArea
} from 'semantic-ui-react';
import { AuthService, CommunityService } from '../../services/DataService';

import './community.css';

const CommunityList = () => {
  const [communities, setCommunities] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const createRef = React.createRef();
  useEffect(() => {
    const fetchData = async () => {
      const data = await CommunityService.getAllCommunities();
      setCommunities(data);
    };
    fetchData();
  }, []);

  const onCreateCommunity = async ({ name, description }) => {
    if (!name) {
      alert('Field "name" is required to create a new field');
      return;
    }
    if (!AuthService.isUserLoggedIn()) {
      alert('You must be logged in to create a post');
      return;
    }
    const res = await CommunityService.createCommunity({
      name,
      description
    });
    if (res !== null) {
      setCommunities([...communities, res]);
    }
  };

  return (
    <div>
      <h1>List of Communities</h1>
      <Divider horizontal>
        <Button
          ref={createRef}
          size="small"
          color="blue"
          onClick={() => {
            setShowModal(true);
          }}
        >
          Create community
        </Button>
      </Divider>
      <div className="community-content">
        <List>
          {communities
            .sort((first, second) => first.name.localeCompare(second.name))
            .map((community) => (
              <List.Item key={community.id}>
                <List.Icon name="newspaper outline" />
                <List.Content>
                  <List.Header>
                    <Link to={`/community/${community.name}`}>
                      {community.name}
                    </Link>
                  </List.Header>
                  <List.Description>{community.description}</List.Description>
                </List.Content>
              </List.Item>
            ))}
        </List>
      </div>
      <Modal
        size="tiny"
        open={showModal}
        onClose={() => setShowModal(false)}
        onOpen={() => setShowModal(true)}
        centered
      >
        <Modal.Header>Create new community</Modal.Header>
        <Modal.Content>
          <Form>
            <Form.Field>
              <Input
                placeholder="Enter community name..."
                onChange={(_event, { value }) => {
                  setName(value);
                }}
              />
            </Form.Field>
            <Form.Field>
              <TextArea
                placeholder="Enter community description"
                onChange={(_event, { value }) => {
                  setDescription(value);
                }}
                rows={3}
              />
            </Form.Field>
          </Form>
        </Modal.Content>
        <Modal.Actions>
          <Button onClick={() => setShowModal(false)}>Cancel</Button>
          <Button
            color="grey"
            content="Create"
            onClick={() => {
              onCreateCommunity({ name, description });
              setShowModal(false);
            }}
          />
        </Modal.Actions>
      </Modal>
    </div>
  );
};

export default CommunityList;
