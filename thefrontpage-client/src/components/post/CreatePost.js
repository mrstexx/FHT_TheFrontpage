import React, { useState } from 'react';
import { Editor } from 'react-draft-wysiwyg';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import { Button, Divider, Grid, Input } from 'semantic-ui-react';

const CreatePost = () => {
  const [editorState, setEditorState] = useState();
  const onEditorStateChange = (event) => {
    setEditorState(event);
  };
  return (
    <div>
      <Grid columns={2}>
        <Grid.Column>
          <Input placeholder="Enter post title..." fluid />
        </Grid.Column>
        <Grid.Column>
          <Input placeholder="Enter image URL..." fluid />
        </Grid.Column>
      </Grid>
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
      <Divider horizontal>
        <Button color="blue">Create post</Button>
      </Divider>
    </div>
  );
};

export default CreatePost;
