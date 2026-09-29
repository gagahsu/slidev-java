import React from 'react';
import {Composition} from 'remotion';
import {ArrayListVsLinkedList, TOTAL_DURATION} from './ArrayListVsLinkedList';

const FPS = 30;

export const Root: React.FC = () => {
  return (
    <>
      <Composition
        id="ArrayListVsLinkedList"
        component={ArrayListVsLinkedList}
        durationInFrames={TOTAL_DURATION}
        fps={FPS}
        width={1920}
        height={1080}
      />
    </>
  );
};
