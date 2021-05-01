import React from 'react';
import { Form, Icon } from 'semantic-ui-react';

import { AuthService } from '../../services/DataService';

const ProfilePage = () => {
  const currentUsername = AuthService.getCurrentUsername();
  return (
    <div>
      <h2>Profile</h2>
      <Form>
        <Form.Field>
          <label>Username</label>
          <input value={currentUsername} disabled />
        </Form.Field>
        <Form.Field>
          <label>E-Mail Address</label>
          <input type="email" value="dummy@email.com" disabled />
        </Form.Field>
      </Form>
    </div>
  );
};

export default ProfilePage;
