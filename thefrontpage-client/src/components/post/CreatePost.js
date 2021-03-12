import React, { useState } from 'react';
import { Editor } from 'react-draft-wysiwyg';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import { Button, Input } from 'semantic-ui-react';

const CreatePost = () => {
  const [editorState, setEditorState] = useState();
  const onEditorStateChange = (event) => {
    setEditorState(event);
  };
  return (
    <div>
      <Input placeholder="Enter post title..." fluid />
      <br></br>
      <Editor
        editorState={editorState}
        editorStyle={{
          border: '1px solid #efefef',
          padding: '5px',
          maxHeight: '200px',
          backgroundColor: 'white'
        }}
        placeholder="Enter post body..."
        toolbarClassName="toolbarClassName"
        wrapperClassName="wrapperClassName"
        editorClassName="editorClassName"
        onEditorStateChange={onEditorStateChange}
      />
      <br></br>
      <Button>Create post</Button>
    </div>
  );
};

export default CreatePost;
