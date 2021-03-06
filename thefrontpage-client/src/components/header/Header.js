import { navigate } from '@reach/router';
import { isEmpty } from 'lodash';
import React, { useState } from 'react';
import { Dropdown, Icon, Menu } from 'semantic-ui-react';

import { AuthService } from '../../services/DataService';

const Header = () => {
  const [activeItem, setActiveItem] = useState('home');
  const handleItemClick = (e, { name }) => {
    setActiveItem(name);
    navigate(name.length > 0 ? `../${name}` : '../');
  };
  const handleProfileClick = () => {
    navigate(`/profile`);
  };
  const onLogout = () => {
    AuthService.logout();
    navigate('/');
    window.location.reload();
  };
  const currentUsername = AuthService.getCurrentUsername();
  return (
    <Menu fixed="top" fluid>
      <Menu.Item name="" onClick={handleItemClick}>
        <b>
          <i className="hashtag icon"></i>TheFrontpage
        </b>
      </Menu.Item>
      <Menu.Item
        name="feed"
        active={activeItem === 'feed'}
        icon="paper plane outline"
        onClick={handleItemClick}
      />
      <Menu.Item
        name="community"
        active={activeItem === 'community'}
        icon="newspaper outline"
        onClick={handleItemClick}
      />
      <Menu.Menu position="right">
        {!isEmpty(currentUsername) ? (
          <Dropdown icon="user circle" item text={currentUsername}>
            <Dropdown.Menu>
              <Dropdown.Item onClick={handleProfileClick}>
                <Icon name="setting" />
                Profile
              </Dropdown.Item>
              <Dropdown.Item onClick={onLogout}>
                <Icon name="log out" />
                Log Out
              </Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        ) : (
          <Menu.Item name="login" icon="sign-in" onClick={handleItemClick} />
        )}
      </Menu.Menu>
    </Menu>
  );
};

export default Header;
