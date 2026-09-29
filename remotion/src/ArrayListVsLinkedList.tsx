import React from 'react';
import {AbsoluteFill, Sequence, useCurrentFrame, interpolate, Easing, spring, useVideoConfig} from 'remotion';
import {Box, Arrow, SceneTitle, Caption} from './components';
import {COLORS, FONT} from './theme';

const CY = 520;

const pop = (frame: number, delay: number, dur = 18) =>
  interpolate(frame, [delay, delay + dur], [0, 1], {extrapolateLeft: 'clamp', extrapolateRight: 'clamp', easing: Easing.out(Easing.cubic)});

const springIn = (frame: number, fps: number, delay: number) =>
  spring({frame: Math.max(0, frame - delay), fps, config: {damping: 14, mass: 0.6}});

// ---------- Title ----------
const TitleScene: React.FC = () => {
  const frame = useCurrentFrame();
  const {fps} = useVideoConfig();
  const s = springIn(frame, fps, 0);
  const fade = pop(frame, 40, 20);
  return (
    <AbsoluteFill style={{background: COLORS.bg, justifyContent: 'center', alignItems: 'center'}}>
      <div style={{transform: `scale(${0.7 + s * 0.3})`, opacity: s, textAlign: 'center'}}>
        <div style={{fontSize: 76, fontWeight: 800, color: COLORS.primary, fontFamily: FONT}}>
          ArrayList vs LinkedList
        </div>
        <div style={{fontSize: 34, color: COLORS.textMuted, marginTop: 20, fontFamily: FONT, opacity: fade}}>
          結構・新增・修改・刪除 元素比較
        </div>
      </div>
    </AbsoluteFill>
  );
};

// ---------- Section divider ----------
const SectionDivider: React.FC<{label: string}> = ({label}) => {
  const frame = useCurrentFrame();
  const {fps} = useVideoConfig();
  const s = springIn(frame, fps, 0);
  return (
    <AbsoluteFill style={{background: COLORS.primary, justifyContent: 'center', alignItems: 'center'}}>
      <div
        style={{
          fontSize: 88,
          fontWeight: 800,
          color: '#ffffff',
          fontFamily: FONT,
          transform: `translateX(${(1 - s) * -300}px)`,
          opacity: s,
        }}
      >
        {label}
      </div>
    </AbsoluteFill>
  );
};

// ---------- ArrayList structure ----------
const alX = (i: number) => 960 + (i - 2) * 170;
const AL_VALUES = [10, 20, 30, 40, 50];

const ArrayStructureScene: React.FC = () => {
  const frame = useCurrentFrame();
  const {fps} = useVideoConfig();
  const highlightAt = 140;
  const hi = pop(frame, highlightAt, 20);
  return (
    <AbsoluteFill style={{background: COLORS.bg}}>
      <SceneTitle title="ArrayList 結構" subtitle="底層由陣列實作 — 連續記憶體空間" />
      {AL_VALUES.map((v, i) => {
        const s = springIn(frame, fps, i * 8 + 10);
        const isHi = i === 2;
        return (
          <Box
            key={i}
            x={alX(i)}
            y={CY}
            label={String(v)}
            sub={`index ${i}`}
            opacity={s}
            scale={0.6 + s * 0.4}
            fill={isHi && hi > 0 ? COLORS.accent : COLORS.accentLight}
            stroke={isHi && hi > 0 ? COLORS.highlight : COLORS.primary}
          />
        );
      })}
      <Caption text="get(2) → 直接以記憶體位移定位，O(1)" opacity={hi} />
    </AbsoluteFill>
  );
};

// ---------- ArrayList add ----------
const ArrayAddScene: React.FC = () => {
  const frame = useCurrentFrame();
  const shiftStart = 40;
  const shiftDur = 40;
  const shiftP = interpolate(frame, [shiftStart, shiftStart + shiftDur], [0, 1], {
    extrapolateLeft: 'clamp',
    extrapolateRight: 'clamp',
    easing: Easing.inOut(Easing.cubic),
  });
  const insertP = pop(frame, shiftStart + shiftDur + 10, 22);
  const captionP = pop(frame, shiftStart + shiftDur + 40, 20);

  // shifting boxes at original index 2,3,4 move one slot right
  const shiftIdx = [2, 3, 4];
  return (
    <AbsoluteFill style={{background: COLORS.bg}}>
      <SceneTitle title="ArrayList.add(2, 99)" subtitle="插入位置之後的元素須整批搬移" />
      <Box x={alX(0)} y={CY} label="10" sub="index 0" />
      <Box x={alX(1)} y={CY} label="20" sub="index 1" />
      {shiftIdx.map((origIdx) => {
        const val = AL_VALUES[origIdx];
        const fromX = alX(origIdx);
        const toX = alX(origIdx + 1);
        const x = fromX + (toX - fromX) * shiftP;
        return (
          <Box
            key={origIdx}
            x={x}
            y={CY}
            label={String(val)}
            sub={`index ${origIdx + (shiftP > 0.5 ? 1 : 0)}`}
            fill={COLORS.accentMuted}
          />
        );
      })}
      <Box
        x={alX(2)}
        y={CY}
        label="99"
        sub="index 2 (新)"
        opacity={insertP}
        scale={0.5 + insertP * 0.5}
        fill={COLORS.accent}
        stroke={COLORS.highlight}
      />
      <Caption text="須搬移後續所有元素，平均時間複雜度 O(n)" opacity={captionP} color={COLORS.danger} />
    </AbsoluteFill>
  );
};

// ---------- ArrayList update ----------
const ArrayUpdateScene: React.FC = () => {
  const frame = useCurrentFrame();
  const flip = interpolate(frame, [30, 55], [0, 1], {extrapolateLeft: 'clamp', extrapolateRight: 'clamp', easing: Easing.inOut(Easing.cubic)});
  const captionP = pop(frame, 60, 20);
  const scaleY = Math.abs(Math.cos(flip * Math.PI));
  const shownVal = flip < 0.5 ? 30 : 77;
  return (
    <AbsoluteFill style={{background: COLORS.bg}}>
      <SceneTitle title="ArrayList.set(2, 77)" subtitle="已知索引，直接覆寫該位置的值" />
      {AL_VALUES.map((v, i) => {
        if (i === 2) {
          return (
            <Box
              key={i}
              x={alX(i)}
              y={CY}
              label={String(shownVal)}
              sub="index 2"
              fill={COLORS.accent}
              stroke={COLORS.highlight}
              scale={1}
              opacity={1}
            />
          );
        }
        return <Box key={i} x={alX(i)} y={CY} label={String(v)} sub={`index ${i}`} />;
      })}
      <div
        style={{
          position: 'absolute',
          left: alX(2) - 60,
          top: CY - 48,
          width: 120,
          height: 96,
          transform: `scaleY(${scaleY === 0 ? 0.01 : scaleY})`,
          transformOrigin: 'center center',
          borderRadius: 14,
          background: COLORS.highlight,
          opacity: 0.35,
        }}
      />
      <Caption text="set(index, value) — O(1)" opacity={captionP} />
    </AbsoluteFill>
  );
};

// ---------- ArrayList delete ----------
const ArrayDeleteScene: React.FC = () => {
  const frame = useCurrentFrame();
  const removeP = interpolate(frame, [20, 45], [0, 1], {extrapolateLeft: 'clamp', extrapolateRight: 'clamp', easing: Easing.in(Easing.cubic)});
  const shiftStart = 50;
  const shiftDur = 40;
  const shiftP = interpolate(frame, [shiftStart, shiftStart + shiftDur], [0, 1], {
    extrapolateLeft: 'clamp',
    extrapolateRight: 'clamp',
    easing: Easing.inOut(Easing.cubic),
  });
  const captionP = pop(frame, shiftStart + shiftDur + 20, 20);
  const shiftIdx = [2, 3, 4];
  return (
    <AbsoluteFill style={{background: COLORS.bg}}>
      <SceneTitle title="ArrayList.remove(1)" subtitle="移除後，後續元素須整批往前搬移" />
      <Box x={alX(0)} y={CY} label="10" sub="index 0" />
      <Box
        x={alX(1)}
        y={CY}
        label="20"
        sub="移除"
        opacity={1 - removeP}
        scale={1 - removeP * 0.6}
        fill={COLORS.dangerLight}
        stroke={COLORS.danger}
      />
      {shiftIdx.map((origIdx) => {
        const val = AL_VALUES[origIdx];
        const fromX = alX(origIdx);
        const toX = alX(origIdx - 1);
        const x = fromX + (toX - fromX) * shiftP;
        return (
          <Box
            key={origIdx}
            x={x}
            y={CY}
            label={String(val)}
            sub={`index ${origIdx - (shiftP > 0.5 ? 1 : 0)}`}
            fill={COLORS.accentMuted}
          />
        );
      })}
      <Caption text="須搬移後續所有元素，平均時間複雜度 O(n)" opacity={captionP} color={COLORS.danger} />
    </AbsoluteFill>
  );
};

// ---------- LinkedList structure ----------
const llX = (i: number) => 460 + i * 340;
const LL_VALUES = [10, 20, 30, 40];

const LinkedNodeArrows: React.FC<{count: number; y?: number; color?: string; progressFor?: (i: number) => number}> = ({
  count,
  y = CY,
  color = COLORS.primary,
  progressFor,
}) => (
  <>
    {Array.from({length: count - 1}).map((_, i) => (
      <Arrow
        key={i}
        x1={llX(i) + 60}
        y1={y}
        x2={llX(i + 1) - 60}
        y2={y}
        color={color}
        progress={progressFor ? progressFor(i) : 1}
      />
    ))}
  </>
);

const LinkedStructureScene: React.FC = () => {
  const frame = useCurrentFrame();
  const {fps} = useVideoConfig();
  const arrowsP = interpolate(frame, [LL_VALUES.length * 8 + 20, LL_VALUES.length * 8 + 50], [0, 1], {
    extrapolateLeft: 'clamp',
    extrapolateRight: 'clamp',
  });
  const headP = pop(frame, 10, 16);
  const nullP = pop(frame, LL_VALUES.length * 8 + 60, 16);
  const captionP = pop(frame, LL_VALUES.length * 8 + 70, 20);
  return (
    <AbsoluteFill style={{background: COLORS.bg}}>
      <SceneTitle title="LinkedList 結構" subtitle="節點分散於記憶體，以指標 (next) 相連" />
      <div
        style={{
          position: 'absolute',
          left: llX(0) - 40,
          top: CY - 150,
          fontSize: 26,
          fontWeight: 700,
          color: COLORS.highlight,
          opacity: headP,
          fontFamily: FONT,
        }}
      >
        head
      </div>
      <Arrow x1={llX(0)} y1={CY - 120} x2={llX(0)} y2={CY - 55} color={COLORS.highlight} opacity={headP} />
      {LL_VALUES.map((v, i) => {
        const s = springIn(frame, fps, i * 8 + 10);
        return (
          <Box
            key={i}
            x={llX(i)}
            y={CY}
            w={130}
            label={String(v)}
            sub={`node ${i}`}
            opacity={s}
            scale={0.6 + s * 0.4}
          />
        );
      })}
      <LinkedNodeArrows count={LL_VALUES.length} progressFor={() => arrowsP} />
      <div
        style={{
          position: 'absolute',
          left: llX(LL_VALUES.length - 1) + 90,
          top: CY - 22,
          fontSize: 26,
          fontWeight: 700,
          color: COLORS.textMuted,
          opacity: nullP,
          fontFamily: FONT,
        }}
      >
        null
      </div>
      <Caption text="新增/刪除節點不需搬移其他節點" opacity={captionP} />
    </AbsoluteFill>
  );
};

// ---------- LinkedList add ----------
const LinkedAddScene: React.FC = () => {
  const frame = useCurrentFrame();
  const oldArrowFade = interpolate(frame, [20, 40], [1, 0], {extrapolateLeft: 'clamp', extrapolateRight: 'clamp'});
  const newNodeIn = pop(frame, 30, 25);
  const riseP = interpolate(frame, [55, 85], [0, 1], {extrapolateLeft: 'clamp', extrapolateRight: 'clamp', easing: Easing.out(Easing.cubic)});
  const newArrowsP = interpolate(frame, [90, 120], [0, 1], {extrapolateLeft: 'clamp', extrapolateRight: 'clamp'});
  const captionP = pop(frame, 125, 20);

  const newNodeY = CY + 220 - riseP * 220;
  const gapX = (llX(0) + llX(1)) / 2;

  return (
    <AbsoluteFill style={{background: COLORS.bg}}>
      <SceneTitle title="LinkedList.add(1, 15)" subtitle="只需改指標指向，不需搬移其他節點" />
      {LL_VALUES.map((v, i) => (
        <Box key={i} x={llX(i)} y={CY} w={130} label={String(v)} sub={`node ${i}`} />
      ))}
      <Arrow x1={llX(0) + 65} y1={CY} x2={llX(1) - 65} y2={CY} opacity={oldArrowFade} />
      <Box x={gapX} y={newNodeY} w={130} label="15" sub="新節點" opacity={newNodeIn} scale={0.6 + newNodeIn * 0.4} fill={COLORS.accent} stroke={COLORS.highlight} />
      <Arrow x1={llX(0) + 65} y1={CY} x2={gapX - 65} y2={newNodeY} curve={riseP > 0.9 ? 0 : 30} color={COLORS.highlight} opacity={newArrowsP} />
      <Arrow x1={gapX + 65} y1={newNodeY} x2={llX(1) - 65} y2={CY} curve={riseP > 0.9 ? 0 : -30} color={COLORS.highlight} opacity={newArrowsP} />
      <LinkedNodeArrows count={2} y={CY} progressFor={() => 0} />
      <Arrow x1={llX(1) + 65} y1={CY} x2={llX(2) - 65} y2={CY} />
      <Arrow x1={llX(2) + 65} y1={CY} x2={llX(3) - 65} y2={CY} />
      <Caption text="插入節點：時間複雜度 O(1)（已知插入位置時）" opacity={captionP} />
    </AbsoluteFill>
  );
};

// ---------- LinkedList update ----------
const LinkedUpdateScene: React.FC = () => {
  const frame = useCurrentFrame();
  const walkP = interpolate(frame, [10, 55], [0, 2], {extrapolateLeft: 'clamp', extrapolateRight: 'clamp', easing: Easing.inOut(Easing.cubic)});
  const walkIdx = Math.min(2, Math.floor(walkP));
  const flip = interpolate(frame, [65, 90], [0, 1], {extrapolateLeft: 'clamp', extrapolateRight: 'clamp', easing: Easing.inOut(Easing.cubic)});
  const scaleY = Math.abs(Math.cos(flip * Math.PI));
  const shownVal = flip < 0.5 ? 30 : 88;
  const captionP = pop(frame, 95, 20);
  return (
    <AbsoluteFill style={{background: COLORS.bg}}>
      <SceneTitle title="LinkedList.set(2, 88)" subtitle="須先從 head 走訪到目標節點" />
      {LL_VALUES.map((v, i) => {
        const visited = i <= walkIdx;
        const isTarget = i === 2;
        return (
          <Box
            key={i}
            x={llX(i)}
            y={CY}
            w={130}
            label={isTarget ? String(shownVal) : String(v)}
            sub={`node ${i}`}
            fill={visited ? COLORS.accent : COLORS.accentLight}
            stroke={isTarget ? COLORS.highlight : COLORS.primary}
          />
        );
      })}
      <LinkedNodeArrows count={LL_VALUES.length} />
      {isFinite(walkIdx) && (
        <div
          style={{
            position: 'absolute',
            left: llX(walkIdx) - 20,
            top: CY - 140,
            fontSize: 40,
            opacity: interpolate(frame, [5, 15, 90, 120], [0, 1, 1, 0], {
              extrapolateLeft: 'clamp',
              extrapolateRight: 'clamp',
            }),
          }}
        >
          🔍
        </div>
      )}
      <div
        style={{
          position: 'absolute',
          left: llX(2) - 65,
          top: CY - 48,
          width: 130,
          height: 96,
          transform: `scaleY(${scaleY === 0 ? 0.01 : scaleY})`,
          transformOrigin: 'center center',
          borderRadius: 14,
          background: COLORS.highlight,
          opacity: 0.35,
        }}
      />
      <Caption text="走訪 O(n) + 修改值 O(1)" opacity={captionP} />
    </AbsoluteFill>
  );
};

// ---------- LinkedList delete ----------
const LinkedDeleteScene: React.FC = () => {
  const frame = useCurrentFrame();
  const highlightP = pop(frame, 10, 20);
  const bypassP = interpolate(frame, [40, 75], [0, 1], {extrapolateLeft: 'clamp', extrapolateRight: 'clamp', easing: Easing.inOut(Easing.cubic)});
  const oldArrowsFade = interpolate(frame, [40, 60], [1, 0], {extrapolateLeft: 'clamp', extrapolateRight: 'clamp'});
  const dropP = interpolate(frame, [80, 110], [0, 1], {extrapolateLeft: 'clamp', extrapolateRight: 'clamp', easing: Easing.in(Easing.cubic)});
  const captionP = pop(frame, 115, 20);

  return (
    <AbsoluteFill style={{background: COLORS.bg}}>
      <SceneTitle title="LinkedList.remove(1)" subtitle="其餘節點位置不變，僅重新連接指標" />
      <Box x={llX(0)} y={CY} w={130} label="10" sub="node 0" />
      <Box
        x={llX(1)}
        y={CY + dropP * 160}
        w={130}
        label="20"
        sub="移除"
        opacity={1 - dropP}
        fill={COLORS.dangerLight}
        stroke={COLORS.danger}
        scale={interpolate(highlightP, [0, 1], [1, 1]) - dropP * 0.3}
      />
      <Box x={llX(2)} y={CY} w={130} label="30" sub="node 1" />
      <Box x={llX(3)} y={CY} w={130} label="40" sub="node 2" />

      <Arrow x1={llX(0) + 65} y1={CY} x2={llX(1) - 65} y2={CY} opacity={oldArrowsFade} />
      <Arrow x1={llX(1) + 65} y1={CY} x2={llX(2) - 65} y2={CY} opacity={oldArrowsFade} />
      <Arrow x1={llX(2) + 65} y1={CY} x2={llX(3) - 65} y2={CY} />

      <Arrow
        x1={llX(0) + 65}
        y1={CY}
        x2={llX(2) - 65}
        y2={CY}
        curve={-70}
        color={COLORS.highlight}
        opacity={bypassP}
      />
      <Caption text="不需搬移其他節點，時間複雜度 O(1)（已知位置時）" opacity={captionP} />
    </AbsoluteFill>
  );
};

// ---------- Ending comparison ----------
const rows: Array<[string, string, string]> = [
  ['底層結構', '連續陣列', '節點 + 指標'],
  ['取值 get(i)', 'O(1)', 'O(n)'],
  ['新增/刪除（頭尾）', '尾端 O(1)／其餘 O(n)', 'O(1)'],
  ['新增/刪除（中間）', 'O(n) 需搬移', 'O(1)（已知節點）'],
  ['記憶體', '較省，需預留容量', '每節點多存一個指標'],
];

const EndingScene: React.FC = () => {
  const frame = useCurrentFrame();
  const titleP = pop(frame, 0, 20);
  return (
    <AbsoluteFill style={{background: COLORS.bg}}>
      <SceneTitle title="總結比較" opacity={titleP} />
      <div style={{position: 'absolute', top: 200, left: 260, right: 260}}>
        <div style={{display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', fontFamily: FONT}}>
          <div style={{fontWeight: 800, fontSize: 30, color: COLORS.textMuted, padding: 16}} />
          <div style={{fontWeight: 800, fontSize: 30, color: COLORS.primary, padding: 16, textAlign: 'center'}}>
            ArrayList
          </div>
          <div style={{fontWeight: 800, fontSize: 30, color: COLORS.primary, padding: 16, textAlign: 'center'}}>
            LinkedList
          </div>
          {rows.map(([label, a, b], i) => {
            const rowP = pop(frame, 20 + i * 22, 18);
            return (
              <React.Fragment key={label}>
                <div
                  style={{
                    fontSize: 26,
                    fontWeight: 700,
                    color: COLORS.text,
                    padding: '18px 16px',
                    borderTop: `2px solid ${COLORS.grid}`,
                    opacity: rowP,
                    transform: `translateY(${(1 - rowP) * 12}px)`,
                  }}
                >
                  {label}
                </div>
                <div
                  style={{
                    fontSize: 26,
                    color: COLORS.text,
                    padding: '18px 16px',
                    textAlign: 'center',
                    borderTop: `2px solid ${COLORS.grid}`,
                    background: COLORS.accentLight,
                    opacity: rowP,
                    transform: `translateY(${(1 - rowP) * 12}px)`,
                  }}
                >
                  {a}
                </div>
                <div
                  style={{
                    fontSize: 26,
                    color: COLORS.text,
                    padding: '18px 16px',
                    textAlign: 'center',
                    borderTop: `2px solid ${COLORS.grid}`,
                    background: '#eef3f3',
                    opacity: rowP,
                    transform: `translateY(${(1 - rowP) * 12}px)`,
                  }}
                >
                  {b}
                </div>
              </React.Fragment>
            );
          })}
        </div>
      </div>
    </AbsoluteFill>
  );
};

// ---------- Root composition ----------
export const ArrayListVsLinkedList: React.FC = () => {
  return (
    <AbsoluteFill style={{background: COLORS.bg}}>
      <Sequence from={0} durationInFrames={201}>
        <TitleScene />
      </Sequence>
      <Sequence from={201} durationInFrames={90}>
        <SectionDivider label="ArrayList" />
      </Sequence>
      <Sequence from={291} durationInFrames={429}>
        <ArrayStructureScene />
      </Sequence>
      <Sequence from={720} durationInFrames={480}>
        <ArrayAddScene />
      </Sequence>
      <Sequence from={1200} durationInFrames={303}>
        <ArrayUpdateScene />
      </Sequence>
      <Sequence from={1503} durationInFrames={339}>
        <ArrayDeleteScene />
      </Sequence>
      <Sequence from={1842} durationInFrames={92}>
        <SectionDivider label="LinkedList" />
      </Sequence>
      <Sequence from={1934} durationInFrames={414}>
        <LinkedStructureScene />
      </Sequence>
      <Sequence from={2348} durationInFrames={537}>
        <LinkedAddScene />
      </Sequence>
      <Sequence from={2885} durationInFrames={475}>
        <LinkedUpdateScene />
      </Sequence>
      <Sequence from={3360} durationInFrames={441}>
        <LinkedDeleteScene />
      </Sequence>
      <Sequence from={3801} durationInFrames={298}>
        <EndingScene />
      </Sequence>
    </AbsoluteFill>
  );
};

export const TOTAL_DURATION = 4099;
