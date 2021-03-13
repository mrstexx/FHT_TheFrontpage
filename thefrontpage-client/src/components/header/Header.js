import { navigate } from '@reach/router';
import React, { useState } from 'react';
import { Dropdown, Menu } from 'semantic-ui-react';

const Header = () => {
  const [activeItem, setActiveItem] = useState('home');
  const handleItemClick = (e, { name }) => {
    setActiveItem(name);
    navigate(name);
  };
  return (
    <Menu fixed="top" fluid>
      <Menu.Item name="/" onClick={handleItemClick}>
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
        <Dropdown icon="user circle" item text="mrstexx">
          <Dropdown.Menu>
            <Dropdown.Item>Profile</Dropdown.Item>
            <Dropdown.Item>Log Out</Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      </Menu.Menu>
    </Menu>
  );
};

export default Header;
