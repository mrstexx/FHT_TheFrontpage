import React, { useState } from 'react';
import { Menu } from 'semantic-ui-react';

const Header = () => {
  const [activeItem, setActiveItem] = useState('home');
  const handleItemClick = (e, { name }) => {
    setActiveItem(name);
  };
  return (
    <Menu fixed="top" fluid>
      <Menu.Item>
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
        <Menu.Item
          icon="sign-out alternate"
          name="logout"
          active={activeItem === 'logout'}
          onClick={handleItemClick}
        />
      </Menu.Menu>
    </Menu>
  );
};

export default Header;